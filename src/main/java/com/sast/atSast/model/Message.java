package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {

    private int uid;
    private int contestId;
    private String contestDescript;
    private int isAccepted;
    private int enable;

}
