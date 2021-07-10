package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUp {
    private int uid;
    private String stuId;
    private String realName;
    private String contestName;
    private int teamId;
    private int enable;

}
