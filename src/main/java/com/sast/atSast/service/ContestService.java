package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.TeamMember;

import java.util.List;

public interface ContestService {

    void createContest(Contest contest);

    void updatepushLink(Long contestId, String pushLink);

    List<TeamMember> getTeamById(Long contestId);

    List<Contest> getContestByCurr(Integer curr);

    List<Contest> getContest();

    void updateCurr(Long contestId, Integer curr);

    void updateComment(Long contestId, String comment);

    void updateJudge(Long judging, Long contestId);

    Contest getContestById(Long contestId);

}
