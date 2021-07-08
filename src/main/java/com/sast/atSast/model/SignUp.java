package com.sast.atSast.model;

import lombok.Data;

@Data
public class SignUp {
    private long uid;
    private String stuId;
    private String realName;
    private String contestName;
    private long teamId;
    private Byte enable;

    public SignUp(long uid, String stuId, String realName, String contestName, long teamId, Byte enable) {
        this.uid = uid;
        this.stuId = stuId;
        this.realName = realName;
        this.contestName = contestName;
        this.teamId = teamId;
        this.enable = enable;
    }
}
