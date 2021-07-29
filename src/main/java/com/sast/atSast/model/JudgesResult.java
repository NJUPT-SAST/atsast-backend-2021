package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgesResult {
  private Long teamId;
  private Long contestId;
  private Long judgeUid;
  private Integer scores;
  private String comment;
  private Byte enable;
  private Long resultId;
}
