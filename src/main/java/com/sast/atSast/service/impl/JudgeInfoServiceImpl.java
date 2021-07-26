package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgeInfoMapper;
import com.sast.atSast.model.JudgeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/24/20:22
 * @Description:
 */
@Service
public class JudgeInfoServiceImpl {

    @Autowired
    JudgeInfoMapper judgeInfoMapper;

    public void insertJudge(JudgeInfo judgeInfo){
        judgeInfoMapper.insertJudge(judgeInfo);
    }
    public void deleteJudge(Long uid){
        judgeInfoMapper.deleteJudge(uid);
    }
    public List<JudgeInfo> listJudges(){
        return judgeInfoMapper.listJudges();
    }
}
