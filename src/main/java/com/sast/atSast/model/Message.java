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
    private Byte isAccepted;
    private Byte enable;
    private long teamId;
    private long leaderUid;
}
