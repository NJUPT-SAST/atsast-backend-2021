package com.sast.atSast.mapper;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.FileStd;
import com.sast.atSast.model.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 比赛管理相关，主要是contest表
 **/
@Repository
@Mapper
public interface ContestMapper {

    void createContest(Contest contest);

    void updatepushLink(long contestId, String pushLink);

    List<TeamMember> getTeamById(long contestId);

    void addFile(FileStd fileStd);

    FileStd getFileMessageById(long stageId, long contestId);

    Contest getContestById(long contestId);

}
