package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contest {
  private long contestId;
  private long masterUid;
  private String contestName;
  private String description;
  private LocalDateTime currAdmin;
  private boolean isTeam;
  private boolean isJoin;
  private boolean judging;
  private String comment;
  private String pushlink;
  private String contestOrganizer;
  private String contestHost;
  private String contestHelper;
  private String currStu;
  private String banners;
  private String teamGroup;
  private String subjectCategory;
  private String workCategory;
  private String joinGrade;
  private String isInstructor;
  private Byte enable;
}
