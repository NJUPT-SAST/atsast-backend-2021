package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.RichtextMapper;
import com.sast.atSast.model.Richtext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RichtextServiceImpl implements RichtextMapper{

    @Autowired
    private RichtextMapper richtextMapper;

    public void updateRichtext(long contestId, long stageId, String content){
        richtextMapper.updateRichtext(contestId,stageId,content);
    }

    public Richtext selectRichtext(long contestId, long stageId){
        return richtextMapper.selectRichtext(contestId,stageId);
    }
}
