package com.sast.atSast.service;

import com.sast.atSast.model.StudentInfo;

import java.util.List;

public interface StudentInfoService {

    void addStudentInfo(StudentInfo studentInfo);

    StudentInfo getStudentInfoById(Long uid);

    String getRealName(Long uid);

    List<StudentInfo> listStudentInfos();

}
