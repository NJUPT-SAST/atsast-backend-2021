package com.sast.atSast.controller;

import com.sast.atSast.model.*;
import com.sast.atSast.server.SseEmitterServer;
import com.sast.atSast.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 身份为学生的用户
 * @author: cxy621,punkginger
 * @create: 2021-07-17 20:31
 **/
@RestController
public class StudentController {

    @Autowired
    StudentInfoService studentInfoService;

    @Autowired
    ContestService contestService;

    @Autowired
    FileService fileService;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    MessageService messageService;


    /**
     * @param studentInfo 学生具体信息
     * @desription 填写个人信息
     */
    @ResponseBody
    @PostMapping("/user/selfinfo")
    @RequiresRoles("student")
    public String addStudentInfo(@RequestBody StudentInfo studentInfo) {
        studentInfoService.addStudentInfo(studentInfo);
        return "ok";
    }

    /**
     * @param uid 学生uid
     * @desription 根据uid获取学生基本信息
     */
    @ResponseBody
    @GetMapping("/user/selfinfo")
    @RequiresRoles("student")
    public StudentInfo getStudentInfoById(Long uid) {
        return studentInfoService.getStudentInfoById(uid);
    }

    /**
     * @param contestId 比赛id
     * @desription 返回比赛全部信息
     */
    @ResponseBody
    @GetMapping("/user/contest/allinfo")
    @RequiresRoles("student")
    public Contest getContestById(Long contestId) {
        return contestService.getContestById(contestId);
    }

    /**
     * @return 返回所有比赛信息
     */
    @ResponseBody
    @GetMapping("/user/contestlist")
    @RequiresRoles("student")
    public List<Contest> getAllContests() {
        return contestService.getContest();
    }

    /**
     * @param file 传来的文件数据
     * @desription 学生上传比赛文件
     */
    @ResponseBody
    @PostMapping("/user/addfiles")
    @RequiresRoles("student")
    public String updateFiles(File file) {
        fileService.updateFiles(file);
        return "ok";
    }

    /**
     *
     * @param teamMember
     * @description 报名并添加对应消息提醒
     */
    @PostMapping("/user/contestapply/team")
    @RequiresRoles("student")
    public String runForTheContest(@RequestBody TeamMember teamMember) {
        teamMemberService.insertTeam(teamMember);
        Message message=teamMember.toMessage();
        messageService.sendInvite(message);
        return "ok";
    }


    /**
     * @description 消息相关
     */
    @ResponseBody
    @GetMapping("/user/message")
    @RequiresRoles("student")
    //按uid查询所有消息
    public List<Message> getMessage(long uid){
        return messageService.getMessage(uid);
    }
    //标记消息为已读
    public void unableMessage(int messageId){
        messageService.unable(messageId);
    }
}
