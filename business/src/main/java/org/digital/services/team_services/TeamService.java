package org.digital.services.team_services;


import lombok.extern.slf4j.Slf4j;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.exceptions.employee_exceptions.EmployeeNotFoundException;
import org.digital.exceptions.project_exceptions.NotFoundProjectException;
import org.digital.exceptions.team_exceptions.EmployeeAlreadyInTeamException;
import org.digital.exceptions.team_exceptions.NullTeamDtoException;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_model.Project;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.employee_services.EmployeeMapper;
import org.digital.services.project_services.ProjectService;
import org.digital.services.team_member_services.MemberMapper;
import org.digital.services.team_member_services.MemberService;
import org.digital.team_dao.TeamRepository;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
import org.digital.team_member_dao.TeamMemberRepository;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class TeamService {
    private TeamRepository repository;
    private MemberService memberService;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamService(TeamRepository repository, MemberService memberService,
                       EmployeeRepository employeeRepository, ProjectRepository projectRepository,
                       TeamMemberRepository teamMemberRepository) {
        this.repository = repository;
        this.memberService = memberService;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
    }


    public MemberCardDto addMemberToTeam(AddMemberDto dto) throws Exception {
        if (dto == null) {
            throw new NullTeamDtoException();
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectCodeName());
        if (optionalProject.isEmpty()) {
            throw new NotFoundProjectException();
        }
        Optional<Team> optionalTeam = repository.findByProject(optionalProject.get());
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            Optional<Employee> optionalEmployee = employeeRepository.findById(dto.getAccountId());

            if (optionalEmployee.isPresent()) {
                Optional<TeamMember> optionalTeamMember = teamMemberRepository.findByMemberAndTeam(optionalEmployee.get(),team);
                if(optionalTeamMember.isPresent()){
                    log.warn("Employee with account id: " + dto.getAccountId().toString() +
                            " is already in team: " + dto.getProjectCodeName() + " with role " + dto.getRole().toString());
                    throw new EmployeeAlreadyInTeamException();
                }else{
                    TeamMember member = memberService.getMemberByEmployeeAndRole(optionalEmployee.get(), EmployeeProjectRole.valueOf(dto.getRole()), team);
                    team.getMembers().add(member);
                    team = repository.save(team);
                    log.info("Employee with account id: " + dto.getAccountId().toString() +
                            " was added to team: " + dto.getProjectCodeName() + " with role " + dto.getRole().toString());
                    return MemberMapper.getMemberCard(optionalEmployee.get(), EmployeeProjectRole.valueOf(dto.getRole()));
                }
//
            } else {
                throw new EmployeeNotFoundException();
            }
        }
        throw new NotFoundProjectException();
    }

    public MemberCardDto removeMemberFromTeam(RemoveMemberDto dto) throws Exception {
        if (dto == null) {
            throw new NullTeamDtoException();
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectCodeName());
        if (optionalProject.isEmpty()) {
            throw new NotFoundProjectException();
        }
        Optional<Team> optionalTeam = repository.findByProject(optionalProject.get());
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            for (var member : team.getMembers()) {
                if (Objects.equals(member.getMember().getAccountId(), dto.getAccountId())) {
                    team.getMembers().remove(member);
                    team = repository.save(team);
                    log.info("Employee with account id: " + dto.getAccountId().toString() +
                            " was removed from team: " + dto.getProjectCodeName());
                    return MemberMapper.getMemberCard(member.getMember(), member.getRole());
                }
            }
            throw new EmployeeNotFoundException();
        } else {
            throw new NotFoundProjectException();
        }
    }


    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) throws Exception {
        if (dto == null) {
            throw new NullTeamDtoException();
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectCodeName());
        if (optionalProject.isEmpty()) {
            throw new NotFoundProjectException();
        }
        Optional<Team> optionalTeam = repository.findByProject(optionalProject.get());
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            List<TeamMember> members = team.getMembers();
            List<MemberCardDto> allTeamMembers = new ArrayList<>();

            for (var member : members) {
                MemberCardDto memberCardDto = new MemberCardDto();
                memberCardDto.setRole(member.getRole());
                memberCardDto.setEmployee(EmployeeMapper.getEmployeeDtoCard(member.getMember()));
                allTeamMembers.add(memberCardDto);
            }
            return allTeamMembers;
        } else {
            throw new NotFoundProjectException();
        }
    }
}
