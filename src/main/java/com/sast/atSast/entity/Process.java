package com.sast.atSast.entity;

import lombok.Data;

@Data
public class Process {

  private Long teamId;
  private String teamName;
  private Long contestId;
  private String fileUrl;
  private Integer score;
  private String result;

}
