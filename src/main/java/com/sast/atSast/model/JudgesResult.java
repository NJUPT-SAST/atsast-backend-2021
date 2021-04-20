package com.sast.atSast.model;


import lombok.Data;

@Data
public class JudgesResult {

  private long teamId;
  private long contestId;
  private long judgeUid;
  private Integer scores;
  private String comment;
  private Byte enable;
}
