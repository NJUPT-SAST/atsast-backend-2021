package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {
  private long teamId;
  private String teamName;
  private long contestId;
  private String fileUrl;
  private Integer score;
  private String result;
  private Byte enable;
}
