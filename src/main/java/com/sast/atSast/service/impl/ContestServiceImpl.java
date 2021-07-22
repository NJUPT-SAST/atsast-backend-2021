package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cxy621
 * @date 2021/7/16 18:48:44
 * @description 创建比赛，将信息插入数据库
 */
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public void createContest(Contest contest){
        contestMapper.createContest(contest);
    }

    @Override
    public void updatepushLink(long contestId, String pushLink){
        contestMapper.updatepushLink(contestId, pushLink);
    }

    @Override
    public List<TeamMember> getTeamById(long contestId) {
        return contestMapper.getTeamById(contestId);
    }

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
    public void updateJudge(Integer judging,long contestId){
        contestMapper.updateJudge(judging, contestId);
    }
    @Override
    public Contest getContestById(long contestId) {
        return contestMapper.getContestById(contestId);
    }

}


