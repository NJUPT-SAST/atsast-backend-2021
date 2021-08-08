package com.sast.atSast.mapper;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.FileStd;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.ContestStage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 比赛管理相关，主要是contest表
 **/
@Mapper
@Repository
public interface ContestMapper {

    void createContest(Contest contest);

    void updatepushLink(Long contestId, String pushLink);

    String getpushLinkById(Long contestId);

    String getfileUrlById(Long contestId);

    List<TeamMember> getTeamById(Long contestId);

    void addFile(FileStd fileStd);

    FileStd getFileMessageById(Long stageId, Long contestId);

    Contest getContestById(Long contestId);

    List<Contest> getContest();

    void updateJudge(Long judging, Long contestId);

    void updateCurr(Long contestId, Integer curr);

    void updateComment(Long contestId, String comment);

    void updateJudge(Integer judging, Long contestId);

    List<Contest> getContestByCurr(Integer curr);

    List<Contest> findAll();

    Contest getInfoById(Long contestId);

    List<ContestStage> getStageById(Long contestId);

}
