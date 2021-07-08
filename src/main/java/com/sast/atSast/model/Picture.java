package com.sast.atSast.model;

import lombok.Data;

@Data
public class Picture {
    private long picId;
    private String picPath;
    private Byte enable;

    public Picture(long picId, String picPath, Byte enable) {
        this.picId = picId;
        this.picPath = picPath;
        this.enable = enable;
    }
}
