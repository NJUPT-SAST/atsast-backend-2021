package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.MessageMapper;
import com.sast.atSast.model.Message;
import com.sast.atSast.server.SseEmitterServer;
import com.sast.atSast.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;


    @Override
    public List<Message> getMessage(Long uid) {
        return messageMapper.getMessage(uid);
    }


    @Override
    public void unable(int messageId) {
        messageMapper.unable(messageId);
    }

    @Override
    public void sendInvite(Message message) {
        String[] splitedUids = message.uid.split("#");
        for (String uid : splitedUids){
            Message msg=new Message();
            msg.uid=uid;
            msg.teamId=message.teamId;
            msg.leaderUid=message.leaderUid;
            msg.contestId=message.contestId;
            msg.leaderName=messageMapper.getLeaderNameById(message.leaderUid);
            msg.contestName=messageMapper.getContestById(message.contestId);
            messageMapper.sendInvite(msg);
            SseEmitterServer.connect(uid);
            SseEmitterServer.sendMessage(uid,"有新的组队消息");
            SseEmitterServer.removeUser(uid);
        }
    }
}
