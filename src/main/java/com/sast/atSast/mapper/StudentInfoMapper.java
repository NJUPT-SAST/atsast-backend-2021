package com.sast.atSast.mapper;

import com.sast.atSast.model.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentInfoMapper {

    void addStudentInfo(StudentInfo studentInfo);

    StudentInfo getStudentInfoById(Long uid);

    String getRealName(Long uid);

    List<StudentInfo> listStudentInfos();

}
