package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Richtext {
    private Long richtextId;
    private String content;
    private Long stageId;
    private Long contestId;
}
