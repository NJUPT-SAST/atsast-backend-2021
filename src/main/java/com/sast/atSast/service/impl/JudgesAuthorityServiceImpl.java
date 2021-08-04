package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.FileMapper;
import com.sast.atSast.mapper.JudgesAuthorityMapper;
import com.sast.atSast.mapper.JudgesResultMapper;
import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.pojo.JudgeResultTemp;
import com.sast.atSast.service.JudgesAuthorityService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JudgesAuthorityServiceImpl implements JudgesAuthorityService {

    @Autowired
    private JudgesAuthorityMapper judgesAuthorityMapper;

    @Autowired
    private JudgesResultMapper judgesResultMapper;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public void addAuthority(JudgesAuthority judgesAuthority) {
        //每个队伍分别存放
        for (Long teamId : judgesAuthority.getTeamIds()) {
            judgesAuthority.setTeamId(teamId);
            judgesAuthorityMapper.addAuthority(judgesAuthority);
        }

        Long uid = judgesAuthority.getJudgeUid();
        Integer judgeTotal = judgesAuthority.getTeamIds().size();
        //授权之后，更改评委的评审状态，并且更新评审队伍数
        judgesAuthorityMapper.updateStageAfterAuthority(uid, judgeTotal);
    }

    @Override
    public JudgeResultTemp getLastResult(Long teamId, Long contestId, Long judgeUid) {
        //查看所需要的队伍id时候在里面
        List<Long> list = judgesAuthorityMapper.getTeamIdsByUid(judgeUid);

        if (!list.contains(teamId)) {
            throw new LocalRuntimeException(CustomError.PERMISSION_DENY);
        }

        JudgesResult judgesResult = judgesResultMapper.getResult(teamId, contestId, judgeUid);
        String comment = judgesResult.getComment();
        Integer scores = judgesResult.getScores();
        List<String> url = Arrays.asList(fileMapper.getFileUrls(teamId).split("#"));
        return new JudgeResultTemp(comment, scores, url);
    }

    @Override
    public String JudgeAuthority(Long contestId, Long judgeUid) {
        List<Long> uids = judgesAuthorityMapper.getJudgeUidsById(contestId);
        if (uids == null) {
            // 没有对应的评委错误
            throw new LocalRuntimeException(CustomError.NO_JUDGES);
        }
        for (Long uid : uids) {
            if (uid.equals(judgeUid)) {
                return "ok";
            }
        }
        //该评委没有评分这个队伍的权限
        throw new LocalRuntimeException(CustomError.NO_RIGHTS);
    }

}
