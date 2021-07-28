package com.sast.atSast.mapper;

import com.sast.atSast.model.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeamMemberMapper {
    List<TeamMember> getTeams(@Param("contestId")Long contestId);
    void updateTeam(@Param("teamId")Long teamId,@Param("score")Integer score,@Param("result")String result);
    void insertTeam(@Param("teamMember")TeamMember teamMember);

}
