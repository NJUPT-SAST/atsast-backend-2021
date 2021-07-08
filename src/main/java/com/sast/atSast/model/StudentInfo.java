package com.sast.atSast.model;

import lombok.Data;

@Data
public class StudentInfo {

  private long uid;
  private String realName;
  private String stuId;
  private String phone;
  private String faculty;
  private String personId;
  private String major;
  private String qqId;
  private String wxId;
  private Byte enable;

  public StudentInfo(long uid, String realName, String stuId, String phone, String faculty, String personId, String major, String qqId, String wxId, Byte enable) {
    this.uid = uid;
    this.realName = realName;
    this.stuId = stuId;
    this.phone = phone;
    this.faculty = faculty;
    this.personId = personId;
    this.major = major;
    this.qqId = qqId;
    this.wxId = wxId;
    this.enable = enable;
  }
}
