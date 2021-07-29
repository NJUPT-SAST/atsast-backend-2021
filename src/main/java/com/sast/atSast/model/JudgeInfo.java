package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeInfo {
    private Long uid;
    private String judgeName;
    private String judgeId;
    private String faculty;
    private String judgeStage;
    private Integer judgeCurr;
    private Integer judgeTotal;
    private String email;
    private Long contestId;
    private Byte enable;
}
