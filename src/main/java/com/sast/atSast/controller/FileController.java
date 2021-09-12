package com.sast.atSast.controller;

import com.sast.atSast.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: 風楪fy
 * @create: 2021-09-12 18:27
 **/
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public String upload(MultipartFile file, String bucketName) {
        fileService.uploadFile(file,bucketName);
        return "ok";
    }

    @GetMapping("download")
    public String download(String bucketName, String fileName, HttpServletResponse httpServletResponse){
        fileService.download(bucketName,fileName,httpServletResponse);
        return "ok";
    }

    @GetMapping("/getUrl")
    public String getDownloadUrl(String bucketName, String fileName) {
        return fileService.getDownloadUrl(bucketName, fileName);
    }
}
