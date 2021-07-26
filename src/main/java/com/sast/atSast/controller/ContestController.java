//package com.sast.atSast.controller;
//
//import com.sast.atSast.enums.CustomError;
//import com.sast.atSast.exception.LocalRuntimeException;
//import com.sast.atSast.service.ContestService;
//import com.sast.atSast.service.ExcelService;
//import com.sast.atSast.service.impl.RichtextServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//public class ContestController {
//    @Autowired
//    private ContestService contestService;
//
//    @Autowired
//    private RichtextServiceImpl richtextService;
//
//    @Autowired
//    private ExcelService excelService;
//
//    @PostMapping("/admin/edittext")
//    public String editText(@RequestParam("contestId") long contestId,
//                           @RequestParam("stageId") long stageId,
//                           @RequestParam("content") String content) {
//        if (richtextService.selectRichtext(contestId, stageId) != null) {
//            richtextService.updateRichtext(contestId, stageId, content);
//            return "success";
//        } else {
//            CustomError.RICH_TEXT_ERROR.setErrMsg("更新富文本信息失败");
//            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
//        }
//    }
//
//    @GetMapping("/admin/edittext")
//    public String listText(@RequestParam("contestId") long contestId,
//                           @RequestParam("stageId") long stageId) {
//        if (richtextService.selectRichtext(contestId, stageId) == null) {
//            CustomError.RICH_TEXT_ERROR.setErrMsg("展示富文本信息失败");
//            throw new LocalRuntimeException(CustomError.RICH_TEXT_ERROR);
//        }
//        return richtextService.selectRichtext(contestId, stageId).getContent();
//    }
//
//    @GetMapping("/admin/exportresult")
//    public String exportResult(@RequestParam("contestId") Integer contestId) throws IOException {
//        return excelService.exportresult(contestId);
//    }
//
//    @PostMapping("/admin/importresult")
//    public String importResult(@RequestParam("file") MultipartFile file,
//                               @RequestParam("contestId") long contestId) {
//        return excelService.importresult(file, contestId);
//    }
//
//
//    @PostMapping("/admin/judging")
//    public String changeJudge(@RequestParam("judging") Integer judging,
//                              @RequestParam("contestId") long contestId) {
//        contestService.updateJudge(judging, contestId);
//        return "success";
//    }
//
//    @GetMapping("/admin/generate")
//    @ResponseBody
//    public String generateList(@RequestParam("contestId") long contestId) throws IOException {
//        return excelService.generatelist(contestId);
//    }
//
//    @ResponseBody
//    @PostMapping("/admin/uploadlist")
//    public String uploadList(MultipartFile file, long contestId) {
//        return excelService.uploadlist(contestId, file);
//    }
//
//
//}
