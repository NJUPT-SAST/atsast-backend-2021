package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgeInfoMapper;
import com.sast.atSast.mapper.JudgesResultMapper;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.service.JudgesResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgesResultServiceImpl implements JudgesResultService {

    @Autowired
    private JudgesResultMapper judgesResultMapper;

    @Autowired
    private JudgeInfoMapper judgeInfoMapper;

    @Override
    public String addResult(JudgesResult judgesResult) {
        // 获取之中是否已经有结果，如果有就执行更新操作，没有就添加
        if (judgesResultMapper.getScore(judgesResult) == null) {
            judgesResultMapper.addResult(judgesResult);

            Long uid = judgesResult.getJudgeUid();
            // 评委状态表已评审队伍 +1
            judgeInfoMapper.addJudgeCurr(uid);
            JudgeInfo judgeInfo = judgeInfoMapper.getJudgeInfoById(uid);
            //全部评审完毕之后返回评审完毕
            if (judgeInfo.getJudgeCurr().equals(judgeInfo.getJudgeTotal())) {
                judgeInfoMapper.updateJudgeStage(uid);
                return "队伍已全部评分完毕";
            }

        } else {
            judgesResultMapper.updateResult(judgesResult);
        }
        return "ok";
    }

    @Override
    public JudgesResult getResult(Long teamId, Long contestId, Long judgeUid) {
        return judgesResultMapper.getResult(teamId, contestId, judgeUid);
    }

    @Override
    public List<JudgesResult> getResults(Long contestId){
        return judgesResultMapper.getResults(contestId);
    }

}
