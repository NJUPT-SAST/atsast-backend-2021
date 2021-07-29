package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.JudgeInfoMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.ExcelService;
import com.sast.atSast.service.impl.RichtextServiceImpl;
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

    @PostMapping("/admin/edittext")
    public String editText(Long contestId, Long stageId, String content) {
        if (richtextService.selectRichtext(contestId, stageId) != null) {
            richtextService.updateRichtext(contestId, stageId, content);
            return "success";
        } else {
            CustomError.RICH_TEXT_ERROR.setErrMsg("更新富文本信息失败");
            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
        }
    }

    @GetMapping("/admin/edittext")
    public String listText(Long contestId, Long stageId) {
        if (richtextService.selectRichtext(contestId, stageId) == null) {
            CustomError.RICH_TEXT_ERROR.setErrMsg("展示富文本信息失败");
            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
        }
        return richtextService.selectRichtext(contestId, stageId).getContent();
    }

    @GetMapping("/admin/exportresult")
    public String exportResult(Long contestId) throws IOException {
        return excelService.exportResult(contestId);
    }

    @PostMapping("/admin/importresult")
    public String importResult(MultipartFile file, Long contestId) {
        return excelService.importResult(file, contestId);
    }

    @PostMapping("/admin/judging")
    public String changeJudge(Long judging, Long contestId) {
        contestService.updateJudge(judging, contestId);
        return "success";
    }

    @GetMapping("/admin/generate")
    @ResponseBody
    public String generateList(Long contestId) throws IOException {
        return excelService.generateList(contestId);
    }

    @PostMapping("/admin/uploadlist")
    public String uploadList(MultipartFile file, Long contestId) {
        return excelService.uploadList(contestId, file);
    }

    @PostMapping("/admin/importjudges")
    public String importjudges(MultipartFile file,long contestId){
        return excelService.importjudge(file,contestId);
    }

    @GetMapping("/admin/deletejudge")
    public String deletejudges(Long uid){
        judgeInfoMapper.deleteJudge(uid);
        return "success";
    }

    @GetMapping("/admin/judgelist")
    public List<JudgeInfo> listjudges(){
        return judgeInfoMapper.listJudges();
    }

}
