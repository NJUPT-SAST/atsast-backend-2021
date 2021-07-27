package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/24/19:52
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeInfo {
    private Long uid;
    private String judgeName;
    private String judgeId;
    private String faculty;
    private String judgeStage;
    private String judgeCurr;
    private String judgeTotal;
    private String email;
    private Long contestId;
}
