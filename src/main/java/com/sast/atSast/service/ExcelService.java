package com.sast.atSast.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/19/10:10
 * @Description:
 */
public interface ExcelService {
    //根据比赛ID导出比赛结果的EXCEL并返回URL
    String exportResult(Long contestId) throws IOException;

    String importResult(MultipartFile file, Long contestId);

    String generateList(Long contestId) throws IOException;

    //导入名单报名
    String uploadList(Long contestId, MultipartFile file);

    //导入名单 生成评委账号
    String importJudge(MultipartFile file, long contestId);

}
