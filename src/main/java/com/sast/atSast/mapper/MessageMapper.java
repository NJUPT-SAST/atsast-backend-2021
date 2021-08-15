package com.sast.atSast.mapper;

import com.sast.atSast.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author punkginger
 * @Date 2021/7/8
 * @Description 消息相关mapper
 */
@Mapper
@Repository
public interface MessageMapper {
    void sendInvite(@Param("msg") Message msg);
    List<Message> getMessage(@Param("uid")Long uid);
    void unable(int messageId);
}
