package com.sast.atSast.enums;

import lombok.AllArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/12/13:15
 * @Description:
 */
@AllArgsConstructor
public enum FacultyEnum {
    TELECOMMUNICATION("通信与信息工程学院"),
    ELECTRON("电子与光学工程学院、微电子学院"),
    COMPUTER("计算机学院、软件学院、网络空间安全学院"),
    INTELLIGENT("自动化院、人工智能学院"),
    MATERIAL("材料科学与工程学院"),
    INTERNET_OF_THINGS("物联网学院"),
    SCIENCE("理学院"),
    GEOGRAPHY("地理与生物信息学院"),
    POST("现代邮政学院"),
    ART("传媒与艺术学院"),
    MANAGEMENT("管理学院"),
    ECONOMY("经济学院"),
    SOCIETY("社会与人口学院"),
    LANGUAGE("外国语学院"),
    EDUCATION("教育与科学技术学院"),
    BELL("贝尔英才学院"),
    OVERSEAS("海外教育学院"),
    TECHNOLOGY("应用与技术学院"),
    PORTLAND("波特兰学院"),
    INNOVATION("创新创业基地");

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    private String facultyName;
}
