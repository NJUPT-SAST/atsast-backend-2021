package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JudgesAuthor {
    private int contestId;
    private int judgeUid;
    private int enable;

}
