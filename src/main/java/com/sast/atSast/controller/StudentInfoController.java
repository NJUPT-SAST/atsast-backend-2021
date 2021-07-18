package com.sast.atSast.controller;

import com.alibaba.fastjson.JSON;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.service.StudentInfoService;
import com.sast.atSast.service.impl.StudentInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ATSAST
 * @description: 学生信息控制器
 * @author: cxy621
 * @create: 2021-07-17 20:31
 **/
@RestController
public class StudentInfoController {

    @Autowired
    StudentInfoService studentInfoService;

    @ResponseBody
    @PostMapping("/user/selfinfo")
    public void addStudentInfo(String data){
        StudentInfo studentInfo = JSON.parseObject(data, StudentInfo.class);
        studentInfoService.addStudentInfo(studentInfo);
        System.out.println(studentInfo);
    }
}
