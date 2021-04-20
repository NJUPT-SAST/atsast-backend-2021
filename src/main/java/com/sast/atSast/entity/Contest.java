package com.sast.atSast.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Contest {
  private long id;
  private long masterUid;
  private String name;
  private String description;
  private Date reg;
  private Date submit;
  private Date judge;
  private Date end;
  private byte curr;
}
