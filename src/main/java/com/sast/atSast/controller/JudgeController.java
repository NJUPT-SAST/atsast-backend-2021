package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.JudgeResultTemp;
import com.sast.atSast.pojo.TeamMemberTemp;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.FileService;
import com.sast.atSast.service.JudgesAuthorityService;
import com.sast.atSast.service.JudgesResultService;
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

    /**
     * @param judgesAuthority 传来的json自动打包成对象
     * @desription 评委授权
     */
    @ResponseBody
    @PostMapping("/admin/authority")
    @RequiresRoles("judge")
    public String addAuthority(@RequestBody JudgesAuthority judgesAuthority) {
        for (Long teamId : judgesAuthority.getTeamIds()) {
            judgesAuthority.setTeamId(teamId);
            judgesAuthorityService.addAuthority(judgesAuthority);
        }

        Long uid = judgesAuthority.getJudgeUid();
        Integer judgeTotal = judgesAuthority.getTeamIds().size();
        judgesAuthorityService.updateStageAfterAuthority(uid, judgeTotal);
        return "ok";
    }

    /**
     * @param judgesResult 添加授权结果
     * @desription 评委打分
     */
    @ResponseBody
    @PostMapping("/judge/comment")
    @RequiresRoles("judge")
    public String addResult(@RequestBody JudgesResult judgesResult) {
        judgesResultService.addResult(judgesResult);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @return 所有符合比赛id的队伍名和id
     * @desription 评审列表
     */
    @ResponseBody
    @GetMapping("/judge/list")
    @RequiresRoles("judge")
    public Object[] getTeamById(long contestId) {
        List<TeamMember> teamMembers = contestService.getTeamById(contestId);
        List<TeamMemberTemp> teamMemberTemps = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            String name = teamMember.getTeamName();
            long id = teamMember.getTeamId();
            TeamMemberTemp teamMemberTemp = new TeamMemberTemp(name, id);
            teamMemberTemps.add(teamMemberTemp);
        }
        return teamMemberTemps.toArray();
    }

    /**
     * @param teamId    队伍id
     * @param contestId 比赛id
     * @param judgeUid  评委uid
     * @desription 获取队伍评分
     */
    @ResponseBody
    @GetMapping("/judge/getcomment")
    @RequiresRoles("judge")
    public JudgeResultTemp getLastResult(Long teamId, Long contestId, Long judgeUid) {

        List<Long> list=judgesAuthorityService.getTeamIdsByUid(judgeUid);

        if(!list.contains(teamId)){
            throw new LocalRuntimeException(CustomError.PERMISSION_DENY);
        }

        JudgesResult judgesResult = judgesResultService.getResult(teamId, contestId, judgeUid);
        String comment = judgesResult.getComment();
        Integer scores = judgesResult.getScores();
        List<String> url = Arrays.asList(fileService.getFileUrls(teamId).split("#"));
        return new JudgeResultTemp(comment, scores, url);
    }

}