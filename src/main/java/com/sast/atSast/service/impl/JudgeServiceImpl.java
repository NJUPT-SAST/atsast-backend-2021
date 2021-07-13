package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgeMapper;
import com.sast.atSast.model.JudgeVO;
import com.sast.atSast.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 風楪fy
 * @create: 2021-07-10 20:13
 **/
@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    JudgeMapper judgeMapper;

    @Override
    public JudgeVO queryJudgeInfo(int teamId) {
        JudgeVO judgeVO = judgeMapper.selectJudgeInfo(teamId);
        judgeVO.setLeaderName(judgeMapper.selectTeamLeader(teamId));
        return judgeVO;
    }
}
