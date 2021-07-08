package com.sast.atSast.model;

import lombok.Data;

@Data
public class JudgesAuthor {
    private long contestId;
    private long judgeUid;
    private Byte enable;

    public JudgesAuthor(long contestId, long judgeUid, Byte enable) {
        this.contestId = contestId;
        this.judgeUid = judgeUid;
        this.enable = enable;
    }
}
