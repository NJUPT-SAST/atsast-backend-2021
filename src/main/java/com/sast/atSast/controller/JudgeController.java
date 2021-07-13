package com.sast.atSast.controller;

import com.sast.atSast.model.JudgeVO;
import com.sast.atSast.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 评委评审相关接口
 **/
@RestController
public class JudgeController {

    @Autowired
    private JudgeService judgeService;
    @GetMapping("/user/judge/show")
    public JudgeVO showJudgeInfo(int judgeUid){
        return judgeService.queryJudgeInfo(judgeUid);
    }
}
