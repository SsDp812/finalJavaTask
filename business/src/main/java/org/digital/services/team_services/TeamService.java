package org.digital.services.team_services;

import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;

import java.util.List;

public interface TeamService {
    public MemberCardDto addMemberToTeam(AddMemberDto dto) throws Exception;

    public MemberCardDto removeMemberFromTeam(RemoveMemberDto dto) throws Exception;

    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) throws Exception;
}
