package com.sast.atSast.mapper;

import com.sast.atSast.model.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentInfoMapper {

    void addStudentInfo(StudentInfo studentInfo);
}
