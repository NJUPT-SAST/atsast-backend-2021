package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.StudentInfoMapper;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: ATSAST
 * @description: 储存添加学生信息
 * @author: cxy621
 * @create: 2021-07-17 20:28
 **/
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Override
    public void addStudentInfo(StudentInfo studentInfo) {
        studentInfoMapper.addStudentInfo(studentInfo);
    }

    @Override
    public StudentInfo getStudentInfoById(long Uid) {
        return studentInfoMapper.getStudentInfoById(Uid);
    }

}
