package com.sast.atSast.service;

import com.sast.atSast.model.Message;

import java.util.List;


public interface MessageService {
    List<Message> getMessage(Long uid);
    void unable(int messageId);
    void sendInvite(Message message);

}
