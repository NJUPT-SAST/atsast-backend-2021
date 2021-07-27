package com.sast.atSast.service;

import com.sast.atSast.model.TeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMemberService {
    List<TeamMember> getTeams(Long contestId);

    void updateTeam(Long teamId, Integer score, String result);

    void insertTeam(TeamMember teamMember);
}
