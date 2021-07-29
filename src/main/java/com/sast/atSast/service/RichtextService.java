package com.sast.atSast.service;

import com.sast.atSast.model.Richtext;

public interface RichtextService {
    void updateRichtext(long contestId, long stageId, String content);

    Richtext selectRichtext(long contestId, long stageId);
}
