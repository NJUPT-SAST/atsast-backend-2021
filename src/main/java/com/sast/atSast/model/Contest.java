package com.sast.atSast.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contest {
  private long contestId;
  private long masterUid;
  private String contestName;
  private String description;
  private Byte currAdmin;
  private String isTeam;
  private String isJoin;
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
  private String enable;
  private String judging;
  private String stages;
  private Byte minMember;
  private Byte maxMember;
  private Byte minInstructor;
  private Byte maxInstructor;
  private String isTech;
  private Byte contestType;
}
