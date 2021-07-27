package com.sast.atSast.mapper;

import com.sast.atSast.model.Stage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: ATSAST
 * @description: stage表操作
 * @author: cxy621
 * @create: 2021-07-17 22:14
 **/
@Mapper
@Repository
public interface StageMapper {

    void createStage(Stage stage);

}
