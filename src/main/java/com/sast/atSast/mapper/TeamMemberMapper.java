package com.sast.atSast.mapper;

import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.LeaderInfo;
import com.sast.atSast.pojo.SummaryTeam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeamMemberMapper {

    List<TeamMember> getTeams(@Param("contestId") Long contestId);

    void updateTeam(@Param("teamId") Long teamId, @Param("score") Integer score, @Param("result") String result);

    void insertTeam(@Param("teamMember") TeamMember teamMember);

    List<SummaryTeam> getSummaryTeamById(@Param("contestId")Long contestId);
    List<TeamMember> getTeamsByLeader(@Param("contestId")Long contestId);
    List<LeaderInfo> getLeaderInfo(@Param("contestId")Long contestId);

}
