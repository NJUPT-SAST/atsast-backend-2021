package com.sast.atSast.controller;

import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.service.impl.ContestServiceImpl;
import com.sast.atSast.service.impl.JudgesAuthorityServiceImpl;
import com.sast.atSast.service.impl.JudgesResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JudgeController {

    @Autowired
    JudgesAuthorityServiceImpl judgesAuthorityService;

    @Autowired
    JudgesResultServiceImpl judgesResultService;

    @Autowired
    ContestServiceImpl contestService;

    /**
     * @description 进行评委授权，一个评委能够评论多个队伍
     * @param judgeId 评委的id
     * @param teamIds 团队的id
     */
    @ResponseBody
    @PostMapping("/admin/editfilestd")
    public void addAuthority(@RequestParam("judgeId") long judgeId, @RequestParam("teamIds") List<Long> teamIds){
        for (long teamId : teamIds){
            judgesAuthorityService.addAuthority(judgeId, teamId);
        }
    }

    /**
     * @desription 添加评审结果
     * @param comment 评论
     * @param scores 分数
     * @param teamId 队伍id
     * @param contestId 比赛id
     * @param judgeUid 评委id
     */
    @ResponseBody
    @PostMapping("/judge/comment")
    public void addResult(@RequestParam("comment") String comment, @RequestParam("scores") int scores,
                          @RequestParam("teamId") long teamId, @RequestParam("contestId") long contestId,
                          @RequestParam("judgeUid") long judgeUid){
        judgesResultService.addResult(comment, scores, teamId, contestId, judgeUid);
    }

    /**
     * @desription 评审列表
     * @param contestId 比赛id
     * @return 所有符合比赛id的队伍名和id
     */
    @ResponseBody
    @PostMapping("/judge/list")
    public List<String> getTeamById(@RequestParam("contestId") long contestId){
        List<TeamMember> teamMembers = contestService.getTeamById(contestId);
        List<String> teamMessage = new ArrayList<>();
        for (TeamMember teamMember : teamMembers){
            teamMessage.add(teamMember.getTeamName() + teamMember.getTeamId());
        }
        System.out.println(teamMessage);
        return teamMessage;
    }
}
