package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.JudgeContestEnd;
import com.sast.atSast.pojo.JudgeCreateContest;

import java.util.List;

public interface ContestService {

    void createContest(Contest contest);

    void updatepushLink(Long contestId, String pushLink);

    String getpushLinkById(Long contestId);

    Object[] getJudgeList(Long contestId);

    List<Contest> getContestByCurr(Integer curr);

    List<Contest> getContest();

    void updateCurr(Long contestId, Integer curr);

    void updateComment(Long contestId, String comment);

    void updateJudge(Long judging, Long contestId);

    Contest getContestById(Long contestId);

    String getfileUrlById(Long contestId);

    JudgeCreateContest judgeContestBegin(Long contestId);

    JudgeContestEnd getContestFiles(Long contestId);

}
