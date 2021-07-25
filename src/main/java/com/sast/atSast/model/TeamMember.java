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
    private long contestId;
    private String teamName;
    private Integer score;
    private String result;
    private String teamGroup;
    private String instructorId;
    private String subjectCategory;
}
