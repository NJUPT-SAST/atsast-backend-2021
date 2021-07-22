package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.TeamMember;

import java.util.List;


public interface ContestService {

    void createContest(Contest contest);
    void updatepushLink(long contestId, String pushLink);
    List<TeamMember> getTeamById(long contestId);
    List<Contest> getContestByCurr(Integer curr);
    List<Contest> getContest();
    Contest getContestById(Integer id);
    void updateCurr(Integer contestId,Integer curr);
    void updateComment(Integer contestId,String comment);
    void updateJudge(Integer judging,long contestId);

    Contest getContestById(long contestId);

}
