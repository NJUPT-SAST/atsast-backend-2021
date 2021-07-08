package com.sast.atSast.model;


import lombok.Data;

@Data
public class JudgesResult {

  private long teamId;
  private long contestId;
  private boolean contestEnd;
  private long judgeUid;
  private Integer scores;
  private String comment;
  private Byte enable;

  public JudgesResult(long teamId, long contestId, boolean contestEnd, long judgeUid, Integer scores, String comment, Byte enable) {
    this.teamId = teamId;
    this.contestId = contestId;
    this.contestEnd = contestEnd;
    this.judgeUid = judgeUid;
    this.scores = scores;
    this.comment = comment;
    this.enable = enable;
  }
}
