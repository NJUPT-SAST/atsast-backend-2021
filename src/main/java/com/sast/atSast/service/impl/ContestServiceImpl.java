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
    public void createContest(Contest contest) {
        contestMapper.createContest(contest);
    }

    @Override
    public void updatepushLink(Long contestId, String pushLink) {
        contestMapper.updatepushLink(contestId, pushLink);
    }

    @Override
    public List<TeamMember> getTeamById(Long contestId) {
        return contestMapper.getTeamById(contestId);
    }

    public List<Contest> getContestByCurr(Integer curr) {
        return contestMapper.getContestByCurr(curr);
    }

    public List<Contest> getContest() {
        return contestMapper.getContest();
    }

    @Override
    public Contest getContestById(Long contestId) {
        return contestMapper.getContestById(contestId);
    }

    @Override
    public void updateCurr(Long contestId, Integer curr) {
        contestMapper.updateCurr(contestId, curr);
    }

    @Override
    public void updateComment(Long contestId, String comment) {
        contestMapper.updateComment(contestId, comment);
    }

    @Override
    public void updateJudge(Long judging, Long contestId) {
        contestMapper.updateJudge(judging, contestId);
    }

}


