package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private long uid;
    private long contestId;
    private String contestDescript;
    private Byte isAccepted;
    private Byte enable;
}
