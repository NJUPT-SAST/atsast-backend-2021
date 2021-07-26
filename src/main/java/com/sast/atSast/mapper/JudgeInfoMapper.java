package com.sast.atSast.mapper;

import com.sast.atSast.model.JudgeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/24/20:10
 * @Description:
 */
@Repository
@Mapper
public interface JudgeInfoMapper {
    void insertJudge(@Param("judgeInfo")JudgeInfo judgeInfo);
    void deleteJudge(@Param("uid")Long uid);
    List<JudgeInfo> listJudges();
}
