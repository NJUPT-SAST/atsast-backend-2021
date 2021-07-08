package com.sast.atSast.model;

import lombok.Data;

@Data
public class Process {

  private long teamId;
  private String teamName;
  private long contestId;
  private String fileUrl;
  private Integer score;
  private String result;
  private Byte enable;

  public Process(long teamId, String teamName, long contestId, String fileUrl, Integer score, String result, Byte enable) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.contestId = contestId;
    this.fileUrl = fileUrl;
    this.score = score;
    this.result = result;
    this.enable = enable;
  }
}
