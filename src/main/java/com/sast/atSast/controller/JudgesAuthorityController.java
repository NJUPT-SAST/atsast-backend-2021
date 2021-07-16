package com.sast.atSast.controller;

import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.service.impl.JudgesAuthorityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JudgesAuthorityController {

    @Autowired
    JudgesAuthorityServiceImpl judgesAuthorityService;

    @ResponseBody
    @PostMapping("/admin/editfilestd")
    public void addAuthority(@RequestParam("judgeId") long judgeId, @RequestParam("teamIds") List<Long> teamIds){
        for (long teamId : teamIds){
            judgesAuthorityService.addAuthority(judgeId, teamId);
        }

    }
}
