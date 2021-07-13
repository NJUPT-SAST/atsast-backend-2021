package com.sast.atSast.controller;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.ContestVO;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 比赛创建管理的接口
 **/
@RestController
@RequestMapping()
public class ContestController {

    @Autowired
    private ContestService contestService;

    //创建比赛
    @PostMapping("/admin/contest/create")
    public void contestCreate(int masterUid, String name, String description,String contestOrganizer,String contestHost,String contestHelper,
                              String reg, String regEnd, String submit, String submitEnd, String judge, String judgeEnd, String show, String showEnd,
                              int isTeam, String instructor, int isJoin, MultipartFile file) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime regLdt = LocalDateTime.parse(reg, formatter);
        LocalDateTime regEndLdt = LocalDateTime.parse(regEnd, formatter);
        LocalDateTime submitLdt = LocalDateTime.parse(submit, formatter);
        LocalDateTime submitEndLdt = LocalDateTime.parse(submitEnd, formatter);
        LocalDateTime judgeLdt = LocalDateTime.parse(judge, formatter);
        LocalDateTime judgeEndLdt = LocalDateTime.parse(judgeEnd, formatter);
        LocalDateTime endLdt = LocalDateTime.parse(show, formatter);
        LocalDateTime endEndLdt = LocalDateTime.parse(showEnd, formatter);
        Contest contest = new Contest(masterUid,name,description,contestOrganizer,contestHost,contestHelper,regLdt,regEndLdt,submitLdt,submitEndLdt,judgeLdt,judgeEndLdt,endLdt,endEndLdt,isTeam,instructor,isJoin);
        contestService.contestCreate(contest);
    }

    //提交照片、视频、推送链接
    @PutMapping("/admin/contest/upload")
    public void uploadInfo(int contestId, List<String> picUrls,List<String> videoUrls, String pushLink){
        contestService.uploadInfo(contestId,picUrls,videoUrls,pushLink);
    }

    //查询比赛报名人数和提交人数
    @GetMapping("/admin/contest/querynum")
    public ContestVO queryNum(int contestId){
        return contestService.queryNum(contestId);
    }

    //查询比赛队伍提交的所有文件
    @GetMapping("/admin/contest/queryfiles")
    public List<String> queryAllFiles(int contestId){
        return contestService.queryAllFiles(contestId);
    }

    //储存权限
    @PostMapping("/admin/contest/saveAuthority")
    public void saveJudgeAuthority(int judgeId,List<Integer> teamIds){
        contestService.saveJudgeAuthority(judgeId,teamIds);
    }
}
