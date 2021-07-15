package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgesResult {
  private long teamId;
  private long contestId;
  private long judgeUid;
  private Integer scores;
  private String comment;
  private Byte enable;
}
