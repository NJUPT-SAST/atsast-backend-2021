package com.sast.atSast.controller;

import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.pojo.JudgeContestEnd;
import com.sast.atSast.pojo.JudgeCreateContest;
import com.sast.atSast.pojo.StageShow;
import com.sast.atSast.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 比赛创建管理的接口
 **/
@RestController
public class SuperadminController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private StageService stageService;

    /**
     * @return 所有比赛信息
     * @desription 列出所有状态的比赛
     */
    @GetMapping("/superadmin/list")
    @ResponseBody
    public List<Contest> contestList() {
        return contestService.getContest();
    }

    /**
     * @param id 比赛id
     * @return 返回比赛的详情信息
     * @desription 返回比赛的详情信息
     */
    @GetMapping("/superadmin/detail/{id}")
    @ResponseBody
    public Contest contestDetail(@PathVariable("id") Long id) {
        return contestService.getContestById(id);
    }

    /**
     * @param contestId 比赛id comment 评审建议 result 结果
     * @return 通过为 1 不通过为 0
     * @desription 审查比赛是否可以发布
     */
    @GetMapping("/superadmin/check/{contestId}")
    @ResponseBody
    public int contestCheck(@PathVariable("contestId") long contestId, String comment, String result) {

        int mark = Integer.parseInt(result);
        ModelAndView mv = new ModelAndView();
        if (mark == 0) {
            contestService.updateComment(contestId, comment);
            return 0;
        }
        contestService.updateCurr(contestId, 1);
        return 1;
    }

    /**
     * @param contestId 比赛id comment 评审建议 result 结果
     * @return 通过为 1 不通过为 0
     * @desription 审查比赛是否可以结束
     */
    @GetMapping("/superadmin/checkend/{contestId}")
    @ResponseBody
    public int contestEndCheck(@PathVariable("contestId") Long contestId, String comment, String result) {

        int mark = Integer.parseInt(result);
        ModelAndView mv = new ModelAndView();
        if (mark == 0) {
            contestService.updateComment(contestId, comment);
            return 0;
        }
        contestService.updateCurr(contestId, 2);
        return 1;
    }

    /**
     * @return 成功则返回"success"
     * @desription 导入Excel生成账号
     */
    @RequestMapping(value = "/superadmin/import", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public String export(MultipartFile file) throws IOException, LocalRuntimeException {
        accountService.readAccountExcel(file);
        return "success";
    }

    //    生成邀请注册链接
    @GetMapping("/superadmin/invite")
    @ResponseBody
    public String invite() {
        String url = " ";
        return url;
    }

    /**
     * @param contestId 比赛id
     * @return 包括照片、视频、推送链节和报销材料
     * @desription 超管审批结束的比赛信息
     */
    @ResponseBody
    @GetMapping("/superadmin/checkend")
    public JudgeContestEnd getContestFiles(Long contestId) {
        JudgeContestEnd judgeContestEnd = new JudgeContestEnd();
        judgeContestEnd.setPictureUrls(Arrays.asList(pictureService.getUrlsById(contestId).split("#")));
        judgeContestEnd.setVideoUrl(videoService.getUrlById(contestId));
        judgeContestEnd.setPushLinkUrls(Arrays.asList(contestService.getpushLinkById(contestId).split("#")));
        judgeContestEnd.setProposalUrl(proposalService.getProposalById(contestId));
        return judgeContestEnd;
    }

    /**
     * @param contestId 比赛id
     * @return 返回比赛信息、状态信息
     * @desription 超管审批管理员创建的比赛
     */
    @ResponseBody
    @GetMapping("/superadmin/check")
    public JudgeCreateContest judgeContestBegin(Long contestId) {
        Contest contest = contestService.getContestById(contestId);
        JudgeCreateContest judgeCreateContest = new JudgeCreateContest();
        judgeCreateContest.setContestName(contest.getContestName());
        judgeCreateContest.setDescription(contest.getDescription());
        judgeCreateContest.setContestOrganizer(contest.getContestOrganizer());
        judgeCreateContest.setContestHost(contest.getContestHost());
        judgeCreateContest.setContestHelper(contest.getContestHelper());
        judgeCreateContest.setIsTeam(contest.getIsTeam());
        judgeCreateContest.setTeamGroup(contest.getTeamGroup());
        judgeCreateContest.setJoinGrade(contest.getJoinGrade());
        judgeCreateContest.setIsInstructor(contest.getIsInstructor());
        judgeCreateContest.setWorkCategory(contest.getWorkCategory());
        judgeCreateContest.setSubjectCategory(contest.getSubjectCategory());
        judgeCreateContest.setFileUrl(contest.getFileUrl());
        judgeCreateContest.setBanners(Arrays.asList(contest.getBanners().split("#")));
        judgeCreateContest.setMinMember(contest.getMinMember());
        judgeCreateContest.setMaxMember(contest.getMaxMember());
        judgeCreateContest.setMinInstructor(contest.getMinInstructor());
        judgeCreateContest.setMaxInstructor(contest.getMaxInstructor());

        List<Stage> stages = stageService.getStagesById(contestId);
        List<StageShow> stageShows = new ArrayList<>();

        for (Stage stage : stages) {
            StageShow stageShow = new StageShow();
            stageShow.setStageName(stage.getStageName());
            stageShow.setStageType(stage.getStageType());
            stageShow.setStageBegin(stage.getStageBegin());
            stageShow.setStageEnd(stage.getStageEnd());
            stageShows.add(stageShow);
        }
        judgeCreateContest.setStages(stageShows);

        return judgeCreateContest;
    }

}
