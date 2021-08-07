package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contest {
    private Long contestId;
    private Long masterUid;
    private String contestName;
    private String description;
    private Byte currAdmin;
    private String isTeam;
    private String isJoin;
    private String comment;
    private String pushlink;
    private String contestOrganizer;
    private String contestHost;
    private String contestHelper;
    private String currStu;
    private String banners;
    private String teamGroup;
    private String subjectCategory;
    private String workCategory;
    private String joinGrade;
    private String isInstructor;
    private Byte enable;
    private String judging;
    private Byte minMember;
    private Byte maxMember;
    private Byte minInstructor;
    private Byte maxInstructor;
    private String isTech;
    private String contestType;
    @Transient
    private List<Stage> stageTemps;
    private Integer stages;
    private String fileUrl;
}
