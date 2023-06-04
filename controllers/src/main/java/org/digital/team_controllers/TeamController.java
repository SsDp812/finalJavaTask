package org.digital.team_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.services.team_services.TeamService;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
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
    public MemberCardDto addMemberToTeam(@RequestBody AddMemberDto dto) throws Exception {
        return service.addMemberToTeam(dto);
    }

    @Operation(summary = "Removing member from team")
    @PostMapping(value = "/remove",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberCardDto removeMemberFromTeam(@RequestBody RemoveMemberDto dto) throws Exception {
        return service.removeMemberFromTeam(dto);
    }

    @Operation(summary = "Get all members of team")
    @PostMapping(value = "/all",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberCardDto> getMembersFromTeam(@RequestBody GetAllMembersDto dto) throws Exception {
        return service.getAllMembers(dto);
    }
}
