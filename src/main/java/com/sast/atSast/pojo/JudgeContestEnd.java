package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 超管结束审批显示信息
 * @author: cxy621
 * @create: 2021-07-26 15:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeContestEnd {
    private List<String> pictureUrls;
    private String videoUrl;
    private List<String> pushLinkUrls;
    private String ProposalUrl;
}
