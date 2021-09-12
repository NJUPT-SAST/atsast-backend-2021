package com.sast.atSast.service.impl;

import com.sast.atSast.pojo.JugdeTemp;
import com.sast.atSast.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: 風楪fy
 * @create: 2021-09-12 19:00
 **/
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public List<JugdeTemp> importjudge(MultipartFile file, Long contestId) {
        return null;
    }

    @Override
    public String exportResult(Long contestId) throws IOException {
        return null;
    }

    @Override
    public String importResult(MultipartFile file, Long contestId) {
        return null;
    }

    @Override
    public String generateList(Long contestId) throws IOException {
        return null;
    }

    @Override
    public String uploadList(Long contestId, MultipartFile file) {
        return null;
    }
}
