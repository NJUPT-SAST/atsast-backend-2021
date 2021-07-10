package com.sast.atSast.mapper;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.ContestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 比赛管理相关，主要是contest表
 **/
@Mapper
public interface ContestMapper {
    public void insertContest(@Param("contest") Contest contest);
    public void insertPic(@Param("contestId")int contestId, @Param("urls") List<String> urls);
    public void insertVideo(@Param("contestId")int contestId, @Param("urls")List<String> urls);
    public void updatePushLink(@Param("contestId")int contestId,@Param("url")String url);
    public ContestVO selectNum(@Param("contestId")int contestId);
    public List<String> selectAllFiles(@Param("contestId")int contestId);
}
