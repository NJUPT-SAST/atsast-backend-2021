package com.sast.atSast.entity;


import lombok.Data;

@Data
public class JudgesResult {

  private long teamId;
  private long contestId;
  private long judgeUid;
  private Integer scores;
  private String comment;
}
