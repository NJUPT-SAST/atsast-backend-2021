package com.sast.atSast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/12/13:52
 * @Description: 各个学院中科技节各分类的队伍数量统计
 */
@Data
@AllArgsConstructor
public class FacultyStatistic {
    private Integer nature;
    private Integer philosophy;
    private Integer inventionA;
    private Integer inventionB;

    public FacultyStatistic() {
        nature = 0;
        philosophy = 0;
        inventionA = 0;
        inventionB = 0;
    }

    public Integer getSum() {
        return nature + philosophy + inventionA + inventionB;
    }
}
