package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JudgesResult {

  private int teamId;
  private int contestId;
  private int contestEnd;
  private int judgeUid;
  private int scores;
  private String comment;
  private int enable;
}
