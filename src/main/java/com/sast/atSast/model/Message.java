package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
<<<<<<< HEAD
    public String uid;
    public Long contestId;
    public Byte enable;
    public Long teamId;
    public Long leaderUid;
    public String contestName;
    public String leaderName;
    public String teamName;
=======
    private Long uid;
    private Long contestId;
    private String isAccepted;
    private Byte enable;
    private Long teamId;
    private Long leaderUid;
>>>>>>> 4160b63d50a1f7f8dd30960c2034203265449ab9
}
