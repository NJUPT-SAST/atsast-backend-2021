package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 存放评分中需要向前端传入的东西
 * @author: cxy621
 * @create: 2021-07-22 14:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeResultTemp {
    private String comment;
    private Integer scores;
    List<String> fileUrls;
}
