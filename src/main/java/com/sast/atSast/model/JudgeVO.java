package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 風楪fy
 * @create: 2021-07-10 14:24
 * 评审详情
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeVO {
    private String contestName;
    private String teamName;
    private String leaderName;
    private String instructorName;
    private List<String> memberNames;
    private List<String> fileUrls;
}
