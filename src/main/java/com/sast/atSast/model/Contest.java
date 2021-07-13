package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contest {
  private int masterUid;
  private int contestId;
  private String name;
  private String description;
  private String contestOrganizer;
  private String contestHost;
  private String contestHelper;
  private LocalDateTime reg;
  private LocalDateTime submit;
  private LocalDateTime judge;
  private LocalDateTime show;
  private LocalDateTime regEnd;
  private LocalDateTime submitEnd;
  private LocalDateTime judgeEnd;
  private LocalDateTime showEnd;
  private int isTeam;
  private String instructor;
  private int isJoin;
  private String comment;
  private int curr;
  private int enable;
  private String pushLink;

  public Contest(int masterUid, String name, String description, String contestOrganizer, String contestHost, String contestHelper, LocalDateTime reg, LocalDateTime submit, LocalDateTime judge, LocalDateTime show, LocalDateTime regEnd, LocalDateTime submitEnd, LocalDateTime judgeEnd, LocalDateTime showEnd, int isTeam, String instructor, int isJoin) {
    this.masterUid = masterUid;
    this.name = name;
    this.description = description;
    this.contestOrganizer = contestOrganizer;
    this.contestHost = contestHost;
    this.contestHelper = contestHelper;
    this.reg = reg;
    this.submit = submit;
    this.judge = judge;
    this.show = show;
    this.regEnd = regEnd;
    this.submitEnd = submitEnd;
    this.judgeEnd = judgeEnd;
    this.showEnd = showEnd;
    this.isTeam = isTeam;
    this.instructor = instructor;
    this.isJoin = isJoin;
  }

}
