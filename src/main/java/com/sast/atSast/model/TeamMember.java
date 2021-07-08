package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {
    private long teamId;
    private long memberUid;
    private Byte enable;
    private long leaderUid;
    private String instructor;
}
