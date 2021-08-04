package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.JudgeResultTemp;
import com.sast.atSast.pojo.TeamMemberTemp;
import com.sast.atSast.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class JudgeController {

    @Autowired
    JudgesAuthorityService judgesAuthorityService;

    @Autowired
    JudgesResultService judgesResultService;

    @Autowired
    ContestService contestService;

    @Autowired
    FileService fileService;

    @Autowired
    JudgeInfoService judgeInfoService;

    /**
     * @param judgesAuthority 传来的json自动打包成对象
     * @desription 评委授权
     */
    @ResponseBody
    @PostMapping("/admin/authority")
    @RequiresRoles("judge")
    public String addAuthority(@RequestBody JudgesAuthority judgesAuthority) {
        judgesAuthorityService.addAuthority(judgesAuthority);
        return "ok";
    }

    /**
     * @param judgesResult 添加授权结果
     * @return 如果没有全部完成返回 "ok"，全部完成返回 “队伍已全部评分完毕”
     * @desription 评委打分
     */
    @ResponseBody
    @PostMapping("/judge/comment")
    @RequiresRoles("judge")
    public String addResult(@RequestBody JudgesResult judgesResult) {
        return judgesResultService.addResult(judgesResult);
    }

    /**
     * @param contestId 比赛id
     * @return 给前端返回一个队伍的队伍名和 id 的列表
     * @desription 评审列表
     */
    @ResponseBody
    @GetMapping("/judge/list")
    @RequiresRoles("judge")
    public Object[] getJudgeList(Long contestId) {
        return contestService.getJudgeList(contestId);
    }

    /**
     * @param teamId    队伍id
     * @param contestId 比赛id
     * @param judgeUid  评委uid
     * @return 返回一个 pojo 中的类
     * @desription 获取队伍评分
     */
    @ResponseBody
    @GetMapping("/judge/getcomment")
    @RequiresRoles("judge")
    public JudgeResultTemp getLastResult(Long teamId, Long contestId, Long judgeUid) {
        return judgesAuthorityService.getLastResult(teamId, contestId, judgeUid);
    }

    /**
     * @param contestId 比赛 id
     * @param judgeUid  评委 uid
     * @return 如果正常返回 ok，否则提出报错
     * @description 判断该评委是否有评论这个队伍的权限
     */
    @GetMapping("/judge/right")
    @RequiresRoles("judge")
    public String JudgeAuthority(Long contestId, Long judgeUid) {
        return judgesAuthorityService.JudgeAuthority(contestId, judgeUid);
    }

}