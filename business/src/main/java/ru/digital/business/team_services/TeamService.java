package ru.digital.business.team_services;

import ru.digital.dto.member_dto.response_member_dto.MemberCardDto;
import ru.digital.dto.team_dto.AddMemberDto;
import ru.digital.dto.team_dto.GetAllMembersDto;
import ru.digital.dto.team_dto.RemoveMemberDto;

import java.util.List;

public interface TeamService {
    public MemberCardDto addMemberToTeam(AddMemberDto dto) throws Exception;

    public MemberCardDto removeMemberFromTeam(RemoveMemberDto dto) throws Exception;

    public List<MemberCardDto> getAllMembers(GetAllMembersDto dto) throws Exception;
}
