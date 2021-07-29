package com.sast.atSast.mapper;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.FileStd;
import com.sast.atSast.model.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 比赛管理相关，主要是contest表
 **/
@Repository
public interface ContestMapper {

    void createContest(Contest contest);

    void updatepushLink(Long contestId, String pushLink);

    String getpushLinkById(Long contestId);

    String getfileUrlById(Long contestId);

    List<TeamMember> getTeamById(Long contestId);

    void addFile(FileStd fileStd);

    FileStd getFileMessageById(Long stageId, Long contestId);

    List<Contest> getContestByCurr(@Param("curr")Integer curr);

    Contest getContestById(Long contestId);

    List<Contest> getContest();

    void updateCurr(Long contestId, @Param("curr")Integer curr);

    void updateComment(Long contestId, String comment);

    void updateJudge(Long judging, Long contestId);

}
