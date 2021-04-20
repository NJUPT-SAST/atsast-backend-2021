package com.sast.atSast.model;

import lombok.Data;

@Data
public class Process {

  private Long teamId;
  private String teamName;
  private Long contestId;
  private String fileUrl;
  private Integer score;
  private String result;
  private Byte enable;

}
