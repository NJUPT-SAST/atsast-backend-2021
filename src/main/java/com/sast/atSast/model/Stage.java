package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    private long contestId;
    private long stageId;
    private String stageName;
    private String stageType;
    private String displayText;
    private LocalDateTime stageBegin;
    private LocalDateTime stageEnd;
}
