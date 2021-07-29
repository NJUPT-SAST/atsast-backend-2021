package com.sast.atSast.service;
import com.sast.atSast.pojo.JugdeTemp;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/19/10:10
 * @Description:
 */
public interface ExcelService {

    List<JugdeTemp> importjudge(MultipartFile file,Long contestId);

    String exportResult(Long contestId) throws IOException;

    String importResult(MultipartFile file, Long contestId);

    String generateList(Long contestId) throws IOException;

    //导入名单报名
    String uploadList(Long contestId, MultipartFile file);

}
