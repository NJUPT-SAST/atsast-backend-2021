package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.FileMapper;
import com.sast.atSast.model.File;
import com.sast.atSast.service.FileService;
import com.sast.atSast.util.MinIoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: ATSAST
 * @description: 上传比赛文件业务逻辑实现层
 * @author: 風楪
 * @create: 2021-09-12 6:36
 **/
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    MinIoUtil minIoUtil;

    /**
     * 上传文件
     * @param file 文件
     * @param bucketName 桶名
     */
    @Override
    public void uploadFile(MultipartFile file,String bucketName) {
        if (!minIoUtil.bucketExists(bucketName)) {
            minIoUtil.makeBucket(bucketName);
        }
        minIoUtil.upload(file, bucketName);
    }

    /**
     * 将文件以流的形式返回
     * @param bucketName 桶名
     * @param fileName 文件名（包括后缀）
     * @param httpServletResponse 响应
     */
    @Override
    public void download(String bucketName, String fileName, HttpServletResponse httpServletResponse) {
        minIoUtil.download(bucketName,fileName,httpServletResponse);
    }

    /**
     * 获取文件下载的url
     * @param bucketName 桶名
     * @param fileName 文件名（包括后缀）
     * @return
     */
    @Override
    public String getDownloadUrl(String bucketName, String fileName) {
        return minIoUtil.getDownloadURL(bucketName, fileName);
    }


}
