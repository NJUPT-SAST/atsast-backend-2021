package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamMember {
    private int teamId;
    private int memberUid;
    private int enable;
    private int leaderUid;
    private String instructor;

}
