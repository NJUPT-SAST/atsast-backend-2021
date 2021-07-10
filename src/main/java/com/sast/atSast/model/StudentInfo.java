package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentInfo {

  private int uid;
  private String realName;
  private String stuId;
  private String phone;
  private String faculty;
  private String personId;
  private String major;
  private String qqId;
  private String wxId;
  private int enable;

}
