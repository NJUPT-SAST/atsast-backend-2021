package com.sast.atSast;

import com.sast.atSast.service.FileStdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
class AtSastApplicationTests {

    @Autowired
    private FileStdService fileStdService;

    @Test
    void contextLoads() throws FileNotFoundException, IOException {

    }

}
