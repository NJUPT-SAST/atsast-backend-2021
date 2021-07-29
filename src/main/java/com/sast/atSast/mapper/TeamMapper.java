package com.sast.atSast.mapper;

import com.sast.atSast.model.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author NJUPT wly
 * @Date 2021/7/18 5:27 下午
 * @Version 1.0
 */
@Mapper
@Repository
public interface TeamMapper {
    int teamCreated(TeamMember teamMember);

    int deleteTeam(int teamId);

    List<TeamMember> getTeam(int teamId);

    int updateScore(int teamId,int score);

    int updateResult(int teamId,String result);
}
