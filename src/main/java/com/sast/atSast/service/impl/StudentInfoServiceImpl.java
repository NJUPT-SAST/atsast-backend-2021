package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.AccountMapper;
import com.sast.atSast.mapper.StudentInfoMapper;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.service.StudentInfoService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 储存添加学生信息
 * @author: cxy621
 * @create: 2021-07-17 20:28
 **/
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void addStudentInfo(StudentInfo studentInfo) {
        Long uid = accountMapper.selectUidByEmail(studentInfo.getEmail());
        studentInfo.setUid(uid);
        studentInfoMapper.addStudentInfo(studentInfo);
    }

    @Override
    public StudentInfo getStudentInfoById(Long Uid) {
        return studentInfoMapper.getStudentInfoById(Uid);
    }

    @Override
    public String getRealName(Long uid) {
        return studentInfoMapper.getRealName(uid);
    }

    @Override
    public List<StudentInfo> listStudentInfos() {
        return studentInfoMapper.listStudentInfos();
    }

}
