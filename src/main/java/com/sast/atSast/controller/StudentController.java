package com.sast.atSast.controller;

import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: ATSAST
 * @description: 学生信息控制器
 * @author: cxy621
 * @create: 2021-07-17 20:31
 **/
@RestController
public class StudentController {

    @Autowired
    StudentInfoService studentInfoService;

    @ResponseBody
    @PostMapping("/user/selfinfo")
    public String addStudentInfo(@RequestBody StudentInfo studentInfo) {
        studentInfoService.addStudentInfo(studentInfo);
        return "ok";
    }

    @ResponseBody
    @GetMapping("/user/selfinfo")
    public StudentInfo getStudentInfoById(long uid) {
        return studentInfoService.getStudentInfoById(uid);
    }

}
