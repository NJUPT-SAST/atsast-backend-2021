package com.sast.atSast.model;

import lombok.Data;

@Data
public class TeamMember {
    private long teamId;
    private long memberUid;
    private Byte enable;
    private long leaderUid;
    private String instructor;

    public TeamMember(long teamId, long memberUid, Byte enable, long leaderUid, String instructor) {
        this.teamId = teamId;
        this.memberUid = memberUid;
        this.enable = enable;
        this.leaderUid = leaderUid;
        this.instructor = instructor;
    }
}
