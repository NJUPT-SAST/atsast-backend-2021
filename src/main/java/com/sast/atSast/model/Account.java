package com.sast.atSast.model;

import lombok.Data;

@Data
public class Account {
    private long uid;
    private String email;
    private String password;
    private Byte type;
    private Byte enable;

    public Account(Long uid, String email, String password, Byte type, Byte enable) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.type = type;
        this.enable = enable;
    }
}
