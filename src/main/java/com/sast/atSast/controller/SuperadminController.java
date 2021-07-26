package com.sast.atSast.controller;

import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.Contest;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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

    /**
     * @param
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
     * @param file
     * @return 成功则返回"success"
     * @desription 导入Excel生成账号
     */
    @RequestMapping(value = "/superadmin/import", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public String export(@RequestParam("file") MultipartFile file) throws IOException, LocalRuntimeException {
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


}
