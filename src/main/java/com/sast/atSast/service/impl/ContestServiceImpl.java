package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
    @Autowired
    private ContestMapper contestMapper;

    public List<Contest> getContestByCurr(Integer curr){
        return contestMapper.getContestByCurr(curr);
    }
    public List<Contest> getContest(){
        return  contestMapper.getContest();
    }
    public Contest getContestById(Integer id){
        return contestMapper.getContestById(id);
    }
    public void updateCurr(Integer contestId,Integer curr){
        contestMapper.updateCurr(contestId,curr);
    }
    public void updateComment(Integer contestId,String comment){
        contestMapper.updateComment(contestId,comment);
    }

}


