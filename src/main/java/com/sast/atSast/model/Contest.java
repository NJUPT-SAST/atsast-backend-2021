package com.sast.atSast.model;


import lombok.Data;

import java.util.Date;

@Data
public class Contest {
  private long masterUid;
  private long contestId;
  private String name;
  private String description;
  private Date reg;
  private Date submit;
  private Date judge;
  private Date end;
  private Date submitEnd;
  private Date judgeEnd;
  private Date endEnd;
  private boolean isTeam;
  private String instructor;
  private String videoId;
  private String picId;
  private boolean isJoin;
  private String comment;
  private byte curr;
  private Byte enable;

  public Contest(long masterUid, long contestId, String name, String description, Date reg, Date submit, Date judge, Date end, Date submitEnd, Date judgeEnd, Date endEnd, boolean isTeam, String instructor, String videoId, String picId, boolean isJoin, String comment, byte curr, Byte enable) {
    this.masterUid = masterUid;
    this.contestId = contestId;
    this.name = name;
    this.description = description;
    this.reg = reg;
    this.submit = submit;
    this.judge = judge;
    this.end = end;
    this.submitEnd = submitEnd;
    this.judgeEnd = judgeEnd;
    this.endEnd = endEnd;
    this.isTeam = isTeam;
    this.instructor = instructor;
    this.videoId = videoId;
    this.picId = picId;
    this.isJoin = isJoin;
    this.comment = comment;
    this.curr = curr;
    this.enable = enable;
  }
}
