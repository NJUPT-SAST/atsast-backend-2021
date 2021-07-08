package com.sast.atSast.model;

import lombok.Data;

@Data
public class Message {

    private long uid;
    private long contestId;
    private String contestDescript;
    private Byte isAccepted;
    private Byte enable;

    public Message(long uid, long contestId, String contestDescript, Byte isAccepted, Byte enable) {
        this.uid = uid;
        this.contestId = contestId;
        this.contestDescript = contestDescript;
        this.isAccepted = isAccepted;
        this.enable = enable;
    }
}
