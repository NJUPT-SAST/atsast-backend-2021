package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    public String uid;
    public Long contestId;
    public Byte enable;
    public Long teamId;
    public Long leaderUid;

}
