package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    private Long contestId;
    private Long stageId;
    private String stageName;
    private String stageType;
    private String displayText;
    private String stageBegin;
    private String stageEnd;
    private Long id;
}
