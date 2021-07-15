package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Richtext {
    private long richtextId;
    private String content;
    private long stageId;
    private long contestId;
}
