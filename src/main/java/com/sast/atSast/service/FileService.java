package com.sast.atSast.service;

import com.sast.atSast.model.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: ATSAST
 * @description: 学生上传比赛文件接口层
 * @author: 風楪
 * @create: 2021-09-12 6:36
 **/
public interface FileService {

    void uploadFile(MultipartFile file, String bucketName);

    void download(String bucketName, String fileName, HttpServletResponse httpServletResponse);

    String getDownloadUrl(String bucketName, String fileName);
}
