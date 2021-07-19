package com.sast.atSast.controller;

import com.sast.atSast.model.Contest;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 比赛创建管理的接口
 **/
@RestController
public class SuperadminController {

    @Autowired
    private ContestService contestService;

////    列出所有状态的比赛
//    @GetMapping("/superadmin/list")
//    @ResponseBody
//    public List<Contest> contestList(){
//        return contestService.getContest();
//    }
//
//    // 比赛详情
//    @GetMapping("/superadmin/detail/{id}")
//    @ResponseBody
//    public Contest contestDetail(@PathVariable("id") int id){
//        return contestService.getContestById(id);
//    }
//
//
////    审查比赛是否可以发布
//    @GetMapping("/superadmin/check/{contestId}/{result}")
//    @ResponseBody
//    public String contestCheck(@PathVariable("result") int result,@PathVariable("contestId") int contestId,String comment){
//        ModelAndView mv=new ModelAndView();
//        if(result==0){
//            contestService.updateComment(contestId,comment);
//            return "NO";
//        }
//        contestService.updateCurr(contestId,1);
//        return "OK";
//    }
//
////    审查比赛是否可以结束
//    @GetMapping("/superadmin/checkend/{contestId}/{result}")
//    @ResponseBody
//    public String contestEndCheck(@PathVariable("result") Integer result,@PathVariable("contestId") Integer contestId,String comment){
//        if(result==0){
//            contestService.updateComment(contestId,comment);
//            return "NO";
//        }
//        contestService.updateCurr(contestId,2);
//        return "OK";
//    }

    //    导入Excel生成账号
    @GetMapping("/superadmin/import")
    @ResponseBody
    public String export(MultipartFile file) {

        return "";
    }

    //    生成邀请注册链接
    @GetMapping("/superadmin/invite")
    @ResponseBody
    public String invite() {
        String url = " ";

        return url;
    }


}
