package com.sast.atSast.entity;

import lombok.Data;

@Data
public class Account {
    private Long uid;
    private String email;
    private String password;
    private Byte type;
}
