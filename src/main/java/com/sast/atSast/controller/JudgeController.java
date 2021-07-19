package com.sast.atSast.controller;

import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.TeamMemberTemp;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.JudgesAuthorityService;
import com.sast.atSast.service.JudgesResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JudgeController {

    @Autowired
    JudgesAuthorityService judgesAuthorityService;

    @Autowired
    JudgesResultService judgesResultService;

    @Autowired
    ContestService contestService;

    /**
     * @param judgesAuthority 传来的json自动打包成对象
     * @desription 评委授权
     */
    @ResponseBody
    @PostMapping("/admin/authority")
    public String addAuthority(@RequestBody JudgesAuthority judgesAuthority) {
        for (long teamId : judgesAuthority.getTeamIds()) {
            judgesAuthority.setTeamId(teamId);
            judgesAuthorityService.addAuthority(judgesAuthority);
        }
        return "ok";
    }

    /**
     * @param judgesResult 添加授权结果
     * @desription 评委打分
     */
    @ResponseBody
    @PostMapping("/judge/comment")
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
    public Object[] getTeamById(long contestId) {
        List<TeamMember> teamMembers = contestService.getTeamById(contestId);
        List<TeamMemberTemp> teamMemberTemps = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            TeamMemberTemp teamMemberTemp = new TeamMemberTemp(teamMember.getTeamName(), teamMember.getTeamId());
            teamMemberTemps.add(teamMemberTemp);
        }
        return teamMemberTemps.toArray();
    }

}