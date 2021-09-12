package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/09/09:48
 * @Description: 汇总评审结果用到的POJO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugdeSummaryTeam {

    private Long teamId;
    private String teamName;
    private String workType;
    private String subjectCategory;
    private Integer totalScore;
    private Double average;
    private Integer judgeNumber;
    private Long leaderUid;
    private String instructor;
    private String instructorId;
    private String teamGroup; //组别 研究生 本科生
    private String faculty; // 学院
    private List<Long> memberUids;

    /**
     * key 工号
     * value 评价与评分
     */
    private Map<String, JugdeResultInfo> scoreMap;
}
