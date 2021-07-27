package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.AccountMapper;
import com.sast.atSast.mapper.JudgeInfoMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.pojo.JugdeTemp;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.ExcelService;
import com.sast.atSast.service.RichtextService;
import com.sast.atSast.service.impl.RichtextServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ContestController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private RichtextServiceImpl richtextService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private JudgeInfoMapper judgeInfoMapper;

    @Autowired
    private AccountMapper accountMapper;

    @PostMapping("/admin/edittext")
    public String edittext(@RequestParam("contestId")long contestId,
                         @RequestParam("stageId")long stageId,
                         @RequestParam("content")String content){
        if(richtextService.selectRichtext(contestId,stageId)!=null) {
            richtextService.updateRichtext(contestId, stageId, content);
            return "success";
        }else {
            CustomError.RICH_TEXT_ERROR.setErrMsg("更新富文本信息失败");
            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
        }
    }

    @GetMapping("/admin/edittext")
    public String listtext(@RequestParam("contestId")long contestId,
                           @RequestParam("stageId")long stageId){
        if(richtextService.selectRichtext(contestId,stageId)==null){
            CustomError.RICH_TEXT_ERROR.setErrMsg("展示富文本信息失败");
            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
        }
        return richtextService.selectRichtext(contestId,stageId).getContent();
    }

    @GetMapping("/admin/exportresult")
    public String exportresult(@RequestParam("contestId")Long contestId) throws IOException {
        return excelService.exportresult(contestId);
    }

    @PostMapping("/admin/importresult")
    public String importresult(@RequestParam("file") MultipartFile file,
                               @RequestParam("contestId")long contestId){
        return excelService.importresult(file,contestId);
    }


    @PostMapping("/admin/judging")
    public String changejudge(@RequestParam("judging")Integer judging,
                              @RequestParam("contestId")long contestId){
        contestService.updateJudge(judging,contestId);
        return "success";
    }

    @GetMapping("/admin/generate")
    public String generatelist(@RequestParam("contestId")long contestId) throws IOException {
        return excelService.generatelist(contestId);
    }

    @PostMapping("/admin/uploadlist")
    public String uploadList(MultipartFile file,long contestId){
        return excelService.uploadlist(contestId,file);
    }

    @PostMapping("/admin/importjudges")
    public List<JugdeTemp> importjudges(MultipartFile file){
        return excelService.importjudge(file);
    }

    @GetMapping("/admin/deletejudge")
    public String deletejudges(Long uid){
        accountMapper.deleteAccountByUid(uid);
        judgeInfoMapper.deleteJudge(uid);
        return "success";
    }

    @GetMapping("/admin/judgelist")
    public List<JudgeInfo> listjudges(){
        return judgeInfoMapper.listJudges();
    }

}
