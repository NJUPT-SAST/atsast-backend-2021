package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/09/13:53
 * @Description: 一个评委所对应的评分和评价
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugdeResultInfo {
    private String comment;
    private Integer score;
}
