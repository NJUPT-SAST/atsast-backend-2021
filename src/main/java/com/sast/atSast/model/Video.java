package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Video {
    private int videoId;
    private int contestId;
    private String videoPath;
    private int enable;

}
