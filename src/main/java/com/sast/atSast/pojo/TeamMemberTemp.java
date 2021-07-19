package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ATSAST
 * @description: 暂时存储需要的列表信息
 * @author: cxy621
 * @create: 2021-07-18 18:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberTemp {
    private String teamName;
    private long teamId;
}
