package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/09/20:29
 * @Description: 队长的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderInfo {
    private Long uid;
    private String faculty;
    private String email;
}
