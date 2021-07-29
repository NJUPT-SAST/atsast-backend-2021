package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgeInfoMapper;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.service.JudgeInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @program: ATSAST
 * @description: 评委具体信息的业务实现层
 * @author: cxy621
 * @create: 2021-07-24 20:49
 **/
@Service
public class JudgeInfoServiceImpl implements JudgeInfoService {

    @Autowired
    JudgeInfoMapper judgeInfoMapper;

    @Override
    public List<JudgeInfo> getJudgeInfo(Long contestId) {
        return judgeInfoMapper.getJudgeInfo(contestId);
    }

    @Override
    public void addSingleJudge(JudgeInfo judgeInfo) {
        judgeInfoMapper.addSingleJudge(judgeInfo);
    }

    @Override
    public Long getUidByEmail(String email) {
        return judgeInfoMapper.getUidByEmail(email);
    }

    @Override
    public void addJudgeCurr(Long uid) {
        judgeInfoMapper.addJudgeCurr(uid);
    }

    @Override
    public JudgeInfo getJudgeInfoById(Long uid) {
        return judgeInfoMapper.getJudgeInfoById(uid);
    }

    @Override
    public void updateJudgeStage(Long uid) {
        judgeInfoMapper.updateJudgeStage(uid);
    }

}
