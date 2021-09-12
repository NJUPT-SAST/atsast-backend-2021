package com.sast.atSast.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/08/18:10
 * @Description:
 */
public interface CreateExcelService {

    //获奖汇总表 决赛结束
    public String createSummary(Long id,String filePath);

    //评审结果表 复赛结束
    //TODO 颜色标记
    public String createJudgeResultSummary(Long contestId,String filePath);

    //TODO 评审汇总表 报名 初赛 复赛结束
    public String createJudgeSummary(Long contestId,String filePath);

    //TODO 个性化学分表 决赛结束
    public String createScoreSummary(Long contestId,String filePath);

    //TODO 签到表 复赛结束
    public String createRegisterSummary(Long contestId,String filePath);

    //TODO 评委评分册 复赛结束（含有评委姓名）
    public String createJudgeScoreSummary(Long contestId,String filePath);

    //TODO 学院统计表 初赛结束
    public String createStaticSummary(Long contestId,String filePath);

    //TODO 报名结束所要调用接口

    //TODO 初赛结束所要调用接口

    //TODO 复赛结束所要调用接口

    //TODO 决赛结束所要调用接口

}
