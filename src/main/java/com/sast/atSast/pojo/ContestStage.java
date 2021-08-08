package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ATSAST
 * @description: 返回阶段，并且返回每个阶段需要传的文件格式规定
 * @author: cxy621
 * @create: 2021-08-08 21:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestStage {
    private Long contestId;
    private Long stageId;
    private String stageName;
    private String stageType;
    private String displayText;
    private String stageBegin;
    private String stageEnd;
    private Long id;
    private Long fileId;
    private String filePath;
    private String fileDescription;
    private String fileLimit;
}
