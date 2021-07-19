package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ATSAST
 * @description: 返回编辑过的文件
 * @author: cxy621
 * @create: 2021-07-19 13:03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTemp {
    private String fileDescription;
    private String fileLimit;
}
