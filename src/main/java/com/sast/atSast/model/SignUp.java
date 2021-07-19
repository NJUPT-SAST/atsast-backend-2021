package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private long uid;
    private String stuId;
    private String realName;
    private String contestName;
    private long teamId;
    private Byte enable;
}
