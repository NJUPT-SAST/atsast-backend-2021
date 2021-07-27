package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 超管审批创建的比赛
 * @author: cxy621
 * @create: 2021-07-27 11:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeCreateContest {
    private String contestName;
    private String description;
    private String contestOrganizer;
    private String contestHost;
    private String contestHelper;
    private String isTeam;
    private String teamGroup;
    private String joinGrade;
    private String isInstructor;
    private String workCategory;
    private String subjectCategory;
    private Byte minMember;
    private Byte maxMember;
    private Byte minInstructor;
    private Byte maxInstructor;
    private List<StageShow> stages;
    private String fileUrl;
    private List<String> banners;
}
