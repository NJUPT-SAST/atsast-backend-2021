package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContestService {
        List<Contest> getContestByCurr(Integer curr);
        List<Contest> getContest();
        Contest getContestById(Integer id);
        void updateCurr(Integer contestId,Integer curr);
        void updateComment(Integer contestId,String comment);
}
