package org.digital.services.team_services;


import jakarta.transaction.Transactional;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.team_member_services.MemberService;
import org.digital.team_dao.TeamRepository;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void addMemberToTeam(AddMemberDto dto) throws Exception {
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            Optional<Employee> optionalEmployee = employeeRepository.findById(dto.getAccountId());
            if(optionalEmployee.isPresent()){
                if(!team.getMembers().contains(memberService.getMemberByEmployeeAndRole(optionalEmployee.get(),EmployeeProjectRole.valueOf(dto.getRole())))){
                    team.getMembers().add(memberService.getMemberByEmployeeAndRole(optionalEmployee.get(),EmployeeProjectRole.valueOf(dto.getRole())));
                    repository.save(team);
                }else{
                    throw new Exception("employee already in team!");
                }
            }else{
                throw new Exception("Error employee id, employee not found!");
            }
        }else{
            throw new Exception("Error project code name, project not found!");
        }
    }

    public void removeMemberFromTeam(RemoveMemberDto dto) throws Exception {
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            team.setMembers(team.getMembers()
                    .stream()
                    .filter(m ->!Objects.equals(m.getMember().getAccountId(),dto.getAccountId()))
                    .collect(Collectors.toList()));
            repository.save(team);
        }else{
            throw new Exception("Error project code name, project not found!");
        }
    }


    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) throws Exception {
        Optional<Team> optionalTeam = repository.findById(dto.getProjectCodeName());
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            List<TeamMember> members = team.getMembers();
            if(members.isEmpty()){
                throw new Exception("Team is empty!");
            }else{
                List<MemberCardDto> allTeamMembers = new ArrayList<>();

                for(var member : members){
                    MemberCardDto memberCardDto = new MemberCardDto();
                    memberCardDto.setRole(member.getRole());
                    memberCardDto.setEmployee(
                            new EmployeeCardDto(
                                    member.getMember().getSurname(),
                                    member.getMember().getName(),
                                    member.getMember().getMiddleName(),
                                    member.getMember().getJobTitle(),
                                    member.getMember().getEmail(),
                                    member.getMember().getEmployeeStatus().toString()
                            )
                    );

                    allTeamMembers.add(memberCardDto);
                }
                return allTeamMembers;
            }

        }else {
            throw new Exception("Error project code name, project not found!");
        }
    }
}
