package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private long uid;
    private String teamName;
    private String teamMembers;
    private String instructorName;
    private String instructorId;
    private String teamGroup;
    private String workType;
    private String subjectType;
    private int enable;
}
