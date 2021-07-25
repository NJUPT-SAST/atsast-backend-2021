package com.sast.atSast.controller;

import com.sast.atSast.mapper.FileMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.File;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.FileService;
import com.sast.atSast.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 身份为学生的用户
 * @author: cxy621
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

    /**
     * @param studentInfo 学生具体信息
     * @desription 填写个人信息
     */
    @ResponseBody
    @PostMapping("/user/selfinfo")
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
    public StudentInfo getStudentInfoById(long uid) {
        return studentInfoService.getStudentInfoById(uid);
    }

    /**
     * @param contestId 比赛id
     * @desription 返回比赛全部信息
     */
    @ResponseBody
    @GetMapping("/user/contest/allinfo")
    public Contest getContestById(long contestId) {
        return contestService.getContestById(contestId);
    }

    @ResponseBody
    @GetMapping("/user/contestlist")
    public List<Contest> getAllContests(){
        return contestService.findAll();
    }

    @ResponseBody
    @GetMapping ("/user/contestInfo")
    public Contest getContestInfo(@RequestParam("contestId") Integer contestId){
        return contestService.getContestById(contestId);
    }

    /**
     * @param file 传来的文件数据
     * @desription 学生上传比赛文件
     */
    @ResponseBody
    @PostMapping("/user/addfiles")
    public String updateFiles(File file) {
        fileService.updateFiles(file);
        return "ok";
    }

}
