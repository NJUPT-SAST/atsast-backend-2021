package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Picture {
    private int picId;
    private int contestId;
    private String picPath;
    private int enable;

}
