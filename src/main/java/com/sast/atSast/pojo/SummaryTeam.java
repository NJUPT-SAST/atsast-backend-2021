package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/08/19:12
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryTeam {
    private Long memberUid;
    private String result;
    private String teamName;
    private String instructor;
}
