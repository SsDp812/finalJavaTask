package org.digital.services.team_services;


import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.exceptions.employee_exceptions.EmployeeNotFoundException;
import org.digital.exceptions.project_exceptions.NotFoundProjectException;
import org.digital.exceptions.team_exceptions.EmployeeAlreadyInTeamException;
import org.digital.exceptions.team_exceptions.NullTeamDtoException;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.employee_services.EmployeeMapper;
import org.digital.services.team_member_services.MemberMapper;
import org.digital.services.team_member_services.MemberService;
import org.digital.team_dao.TeamRepository;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {
    private TeamRepository repository;
    private MemberService memberService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public TeamService(TeamRepository repository,MemberService memberService, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.memberService = memberService;
        this.employeeRepository = employeeRepository;
    }


    public MemberCardDto addMemberToTeam(AddMemberDto dto) throws Exception {
        if(dto == null){
            throw new NullTeamDtoException();
        }
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            Optional<Employee> optionalEmployee = employeeRepository.findById(dto.getAccountId());
            if(optionalEmployee.isPresent()){
                if(!team.getMembers().contains(memberService.getMemberByEmployeeAndRole(optionalEmployee.get(),EmployeeProjectRole.valueOf(dto.getRole())))){
                    team.getMembers().add(memberService.getMemberByEmployeeAndRole(optionalEmployee.get(),EmployeeProjectRole.valueOf(dto.getRole())));
                    repository.save(team);
                    return MemberMapper.getMemberCard(optionalEmployee.get(),EmployeeProjectRole.valueOf(dto.getRole()));
                }else{
                    throw new EmployeeAlreadyInTeamException();
                }
            }else{
                throw new EmployeeNotFoundException();
            }
        }else{
            throw new NotFoundProjectException();
        }
    }

    public MemberCardDto removeMemberFromTeam(RemoveMemberDto dto) throws Exception {
        if(dto == null){
            throw new NullTeamDtoException();
        }
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
           for(var member : team.getMembers()){
               if(Objects.equals(member.getMemberId(),dto.getAccountId())){
                   team.getMembers().remove(member);
                   repository.save(team);
                   return MemberMapper.getMemberCard(member.getMember(),member.getRole());
               }
           }
           throw new EmployeeNotFoundException();
        }else{
            throw new NotFoundProjectException();
        }
    }


    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) throws Exception {
        if(dto == null){
            throw new NullTeamDtoException();
        }
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            List<TeamMember> members = team.getMembers();
            List<MemberCardDto> allTeamMembers = new ArrayList<>();

            for(var member : members){
                MemberCardDto memberCardDto = new MemberCardDto();
                memberCardDto.setRole(member.getRole());
                memberCardDto.setEmployee(EmployeeMapper.getEmployeeDtoCard(member.getMember()));
                allTeamMembers.add(memberCardDto);
            }
            return allTeamMembers;
        }else {
            throw new NotFoundProjectException();
        }
    }
}
