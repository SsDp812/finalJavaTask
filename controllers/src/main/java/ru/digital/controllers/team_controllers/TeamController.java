package ru.digital.controllers.team_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.digital.dto.member_dto.response_member_dto.MemberCardDto;
import ru.digital.business.team_services.Impls.TeamServiceImpl;
import ru.digital.business.team_services.TeamService;
import ru.digital.dto.team_dto.AddMemberDto;
import ru.digital.dto.team_dto.GetAllMembersDto;
import ru.digital.dto.team_dto.RemoveMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
@Tag(name = "TeamController", description = "Controller for team")
public class TeamController {
    private TeamService service;

    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    @Operation(summary = "Adding member to team")
    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberCardDto addMemberToTeam(@RequestBody @Valid AddMemberDto dto) throws Exception {
        return service.addMemberToTeam(dto);
    }

    @Operation(summary = "Removing member from team")
    @PostMapping(value = "/remove",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberCardDto removeMemberFromTeam(@RequestBody @Valid RemoveMemberDto dto) throws Exception {
        return service.removeMemberFromTeam(dto);
    }

    @Operation(summary = "Get all members of team")
    @PostMapping(value = "/all",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberCardDto> getMembersFromTeam(@RequestBody @Valid GetAllMembersDto dto) throws Exception {
        return service.getAllMembers(dto);
    }
}
