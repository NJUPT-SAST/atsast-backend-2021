package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ATSAST
 * @description: 审批展示的阶段数据
 * @author: cxy621
 * @create: 2021-07-27 12:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageShow {
    private String stageName;
    private String stageType;
    private String stageBegin;
    private String stageEnd;
}
