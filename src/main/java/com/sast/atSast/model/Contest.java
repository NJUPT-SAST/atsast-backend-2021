package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
