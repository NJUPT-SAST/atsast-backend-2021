package com.sast.atSast.service;

import com.sast.atSast.model.StudentInfo;

import java.util.List;

public interface StudentInfoService {

    void addStudentInfo(StudentInfo studentInfo);

    StudentInfo getStudentInfoById(long Uid);

    String getRealName(long uid);

    List<StudentInfo> listStudentInfos();

}
