package ru.digital.business.team_services.Impls;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.business.employee_services.EmployeeMapper;
import ru.digital.business.team_member_services.MemberMapper;
import ru.digital.business.team_member_services.MemberService;
import ru.digital.business.team_services.TeamService;
import ru.digital.commons.exceptions.employee_exceptions.EmployeeNotFoundException;
import ru.digital.commons.exceptions.project_exceptions.NotFoundProjectException;
import ru.digital.commons.exceptions.team_exceptions.EmployeeAlreadyInTeamException;
import ru.digital.commons.exceptions.team_exceptions.NullTeamDtoException;
import ru.digital.dao.employee_dao.EmployeeRepository;
import ru.digital.dao.project_dao.ProjectRepository;
import ru.digital.dao.team_dao.TeamRepository;
import ru.digital.dao.team_member_dao.TeamMemberRepository;
import ru.digital.dto.member_dto.response_member_dto.MemberCardDto;
import ru.digital.dto.team_dto.AddMemberDto;
import ru.digital.dto.team_dto.GetAllMembersDto;
import ru.digital.dto.team_dto.RemoveMemberDto;
import ru.digital.models.employee_model.Employee;
import ru.digital.models.project_model.Project;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class TeamServiceImpl implements TeamService {
    private TeamRepository repository;
    private MemberService memberService;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository, MemberService memberService,
                           EmployeeRepository employeeRepository, ProjectRepository projectRepository,
                           TeamMemberRepository teamMemberRepository) {
        this.repository = repository;
        this.memberService = memberService;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
    }


    public MemberCardDto addMemberToTeam(AddMemberDto dto) {
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
                Optional<TeamMember> optionalTeamMember = teamMemberRepository.findByMemberAndTeam(optionalEmployee.get(), team);
                if (optionalTeamMember.isPresent()) {
                    log.warn("Employee with account id: " + dto.getAccountId().toString() +
                            " is already in team: " + dto.getProjectCodeName() + " with role " + dto.getRole().toString());
                    throw new EmployeeAlreadyInTeamException();
                } else {
                    TeamMember member = memberService.getMemberByEmployeeAndRole(optionalEmployee.get(), dto.getRole(), team);
                    team.getMembers().add(member);
                    team = repository.save(team);
                    log.info("Employee with account id: " + dto.getAccountId().toString() +
                            " was added to team: " + dto.getProjectCodeName() + " with role " + dto.getRole().toString());
                    return MemberMapper.getMemberCard(optionalEmployee.get(), dto.getRole());
                }
//
            } else {
                throw new EmployeeNotFoundException();
            }
        }
        throw new NotFoundProjectException();
    }

    public MemberCardDto removeMemberFromTeam(RemoveMemberDto dto) {
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
                    teamMemberRepository.delete(member);
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


    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) {
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
