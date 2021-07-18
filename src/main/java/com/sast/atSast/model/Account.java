package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private long uid;
    private String email;
    private String password;
    private String role;
    private String salt;
    private Byte enable;
}
