package com.sast.atSast.controller;

import com.sast.atSast.model.*;
import com.sast.atSast.pojo.FileTemp;
import com.sast.atSast.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStdService fileStdService;

//    @Autowired
//    private ExcelService excelService;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private JudgeInfoService judgeInfoService;

    /**
     * @param contest 前端传递json字符串自动打包成对象
     * @description 前端传递json字符串，springboot中可以自动打包
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/createcontest")
    public String createContest(@RequestBody Contest contest) {
        contestService.createContest(contest);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param pushLink  推送链接url(s)
     * @description 通过对应的比赛id，更新对应的推送链接
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/uploadlink")
    public String addpushLink(Long contestId, String pushLink) {
        contestService.updatepushLink(contestId, pushLink);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param videoUrl  视频url
     * @desription 上传视频url（单个）
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/uploadvideo")
    public String getVideo(Long contestId, String videoUrl) {
        videoService.addVideo(contestId, videoUrl);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param picUrls   照片urls
     * @desription 上传照片urls
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/updatepic")
    public String addPictures(Long contestId, String picUrls) {
        pictureService.addPictures(contestId, picUrls);
        return "ok";
    }

    /**
     * @param fileStd 自动装配为对象
     * @desription 管理员设定提交文件的各种信息
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/editfilestd")
    public String addFile(@RequestBody FileStd fileStd) {
        fileStdService.addFile(fileStd);
        return "ok";
    }

    /**
     * @param stageId   阶段id
     * @param contestId 比赛id
     * @desription 获取设定文件信息
     */
    @ResponseBody
    @RequiresRoles("admin")
    @GetMapping("/admin/editfilestd")
    public FileTemp getFileMessageById(Long stageId, Long contestId) {
        FileStd fileStd = fileStdService.getFileMessageById(stageId, contestId);
        return new FileTemp(fileStd.getFileDescription(), fileStd.getFileLimit());
    }

    /**
     * @param proposal 报销材料的所需要的信息
     * @description 提交报销材料
     */
    @ResponseBody
    @RequiresRoles("admin")
    @PostMapping("/admin/addendfile")
    public String addProposalFile(@RequestBody Proposal proposal) {
        proposalService.addProposalFile(proposal);
        return "ok";
    }

    /**
     * @param judgeInfo 评委的具体信息
     * @description 创建单个评委
     */
    @ResponseBody
    @PostMapping("/admin/addsingle")
    public String addSingleJudge(@RequestBody JudgeInfo judgeInfo) {
        Long uid = judgeInfoService.getUidByEmail(judgeInfo.getEmail());
        judgeInfo.setUid(uid);
        judgeInfoService.addSingleJudge(judgeInfo);
        return "ok";
    }

}
