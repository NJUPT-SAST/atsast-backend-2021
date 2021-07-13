package com.sast.atSast.mapper;

import com.sast.atSast.model.JudgeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 评审相关，judge_result、judge_authority表
 **/
@Mapper
public interface JudgeMapper {
    JudgeVO selectJudgeInfo(@Param("teamId") int teamId);
    String selectTeamLeader(@Param("teamId") int teamId);
    List<Integer> selectJudgeAuthority(@Param("judgeUid") int judgeUid);
}
