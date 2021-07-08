package com.sast.atSast.model;

import lombok.Data;

@Data
public class Video {
    private long videoId;
    private long videoPath;
    private Byte enable;

    public Video(long videoId, long videoPath, Byte enable) {
        this.videoId = videoId;
        this.videoPath = videoPath;
        this.enable = enable;
    }
}
