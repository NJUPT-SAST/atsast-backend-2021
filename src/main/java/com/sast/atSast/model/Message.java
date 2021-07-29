package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long uid;
    private Long contestId;
    private String isAccepted;
    private Byte enable;
    private Long teamId;
    private Long leaderUid;
}
