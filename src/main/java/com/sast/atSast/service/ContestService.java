package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.model.TeamMember;

import java.util.List;


public interface ContestService {

    void createContest(Contest contest);

    void updatepushLink(long contestId, String pushLink);

    List<TeamMember> getTeamById(long contestId);

}
