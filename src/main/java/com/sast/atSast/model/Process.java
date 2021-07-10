package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Process {

  private int teamId;
  private String teamName;
  private int contestId;
  private String fileUrl;
  private int score;
  private String result;
  private int enable;

}
