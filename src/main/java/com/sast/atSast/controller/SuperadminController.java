package com.sast.atSast.controller;

import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.Contest;
import com.sast.atSast.server.RedisServer;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.util.RandomString;
import com.sast.atSast.model.Stage;
import com.sast.atSast.pojo.JudgeContestEnd;
import com.sast.atSast.pojo.JudgeCreateContest;
import com.sast.atSast.pojo.StageShow;
import com.sast.atSast.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

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
    RedisServer redisServer = new RedisServer();

    /**
     * @return 所有比赛信息
     * @desription 列出所有状态的比赛
     */
    @GetMapping("/superadmin/list")
    @ResponseBody
    @RequiresRoles("superadmin")
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
    @RequiresRoles("superadmin")
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
    @RequiresRoles("superadmin")
    public int contestCheck(@PathVariable("contestId") Long contestId, String comment, String result) {

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
    @RequiresRoles("superadmin")
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
     * @desription 导入Excel生成管理员账号
     */
    @RequestMapping(value = "/superadmin/import", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("superadmin")
    public String export(@RequestParam("file") MultipartFile file) throws IOException, LocalRuntimeException {
        accountService.readAccountExcel(file);
        return "success";
    }

    /**
     * @return 返回一个value 为一个随机字符串 前端携带用来注册使用
     * @description 生成邀请注册链接
     */
    @GetMapping("/superadmin/invite")
    @ResponseBody
    @RequiresRoles("superadmin")
    public String invite() {
        String key = "key";
        Jedis jedis = new Jedis(redisServer.getHost());
        jedis.auth(redisServer.getPassword());

        if (jedis.get(key) != null) {
            return jedis.get(key);
        }

        String value = RandomString.getRandomString(8);
        jedis.set(key, value);
        jedis.expire(key, 60 * 30);

        return value;
    }

    /**
     * @param contestId 比赛id
     * @return 包括照片、视频、推送链节和报销材料
     * @desription 超管审批结束的比赛信息
     */
    @ResponseBody
    @GetMapping("/superadmin/checkend")
    @RequiresRoles("superadmin")
    public JudgeContestEnd getContestFiles(Long contestId) {
        return contestService.getContestFiles(contestId);
    }

    /**
     * @param contestId 比赛id
     * @return 返回管理创建的比赛的所有信息
     * @desription 超管审批管理员创建的比赛
     */
    @ResponseBody
    @GetMapping("/superadmin/check")
    @RequiresRoles("superadmin")
    public JudgeCreateContest judgeContestBegin(Long contestId) {
        return contestService.judgeContestBegin(contestId);
    }

}
