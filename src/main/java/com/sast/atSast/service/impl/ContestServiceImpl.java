package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.ContestVO;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 風楪fy
 * @create: 2021-07-08 15:54
 **/
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestMapper contestMapper;

    @Override
    public void contestCreate(Contest contest) {
        //比赛不能任意加入，则导入
        if (contest.getIsJoin() == 0) {
            //todo 等学生操作的service
        }
        contestMapper.insertContest(contest);
    }

    @Override
    public void uploadInfo(int contestId, List<String> picUrls, List<String> videoUrls, String pushLink) {
        contestMapper.insertPic(contestId,picUrls);
        contestMapper.insertVideo(contestId,videoUrls);
        contestMapper.updatePushLink(contestId,pushLink);
    }

    @Override
    public ContestVO queryNum(int contestId) {
        return contestMapper.selectNum(contestId);
    }

    @Override
    public List<String> queryAllFiles(int contestId) {
        return contestMapper.selectAllFiles(contestId);
    }

    @Override
    public void saveJudgeAuthority(int judgeUid, List<Integer> teamIds) {
        contestMapper.insertJudgeAuthority(judgeUid,teamIds);
    }

}
