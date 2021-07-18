package com.sast.atSast.controller;

import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.JudgesAuthorityService;
import com.sast.atSast.service.JudgesResultService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
     * @desription 评委授权
     * @param judgesAuthority 传来的json自动打包成对象
     */
    @ResponseBody
    @PostMapping("/admin/authority")
    public void addAuthority(@RequestBody JudgesAuthority judgesAuthority){
        for (long teamId : judgesAuthority.getTeamIds()){
            judgesAuthority.setTeamId(teamId);
            judgesAuthorityService.addAuthority(judgesAuthority);
        }
    }


    @ResponseBody
    @PostMapping("/judge/comment")
    public void addResult(@RequestBody JudgesResult judgesResult){
        judgesResultService.addResult(judgesResult);
    }

//    /**
//     * @desription 评审列表
//     * @param contestId 比赛id
//     * @return 所有符合比赛id的队伍名和id
//     */
//    @ResponseBody
//    @GetMapping("/judge/list")
//    public List<String> getTeamById(@RequestParam("contestId") long contestId){
//        List<TeamMember> teamMembers = contestService.getTeamById(contestId);
//        List<String> teamMessage = new ArrayList<>();
//        for (TeamMember teamMember : teamMembers){
//            teamMessage.add(teamMember.getTeamName() + teamMember.getTeamId());
//        }
//        System.out.println(teamMessage);
//        return teamMessage;
//    }

}
