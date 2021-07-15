package com.sast.atSast.mapper;

import com.sast.atSast.model.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 比赛管理相关，主要是contest表
 **/
@Repository
@Mapper
public interface ContestMapper {
    List<Contest> getContestByCurr(@Param("curr")Integer curr);

    Contest getContestById(@Param("id")Integer id);

    List<Contest> getContest();

    void updateCurr(@Param("contestId")Integer contestId,@Param("curr")Integer curr);

    void updateComment(@Param("contestId")Integer contestId,@Param("comment")String comment);

}
