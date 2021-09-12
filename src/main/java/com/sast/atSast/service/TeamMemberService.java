package com.sast.atSast.service;

import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.LeaderInfo;
import com.sast.atSast.pojo.SummaryTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMemberService {

    List<TeamMember> getTeams(Long contestId);

    void updateTeam(Long teamId, Integer score, String result);

    void insertTeam(TeamMember teamMember);

    //获得汇总信息（奖状相关）
    List<List<SummaryTeam>> getSummaryTeams(Long contestId);

    List<TeamMember> getTeamsByLeader(Long contestId);

    List<LeaderInfo> getLeaderInfo(Long contestId);


}
