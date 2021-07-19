package com.sast.atSast;

import com.sast.atSast.model.FileStd;
import com.sast.atSast.service.FileStdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
class AtSastApplicationTests {

//	private static final String PATH = "D:\\CXY\\EXE\\2021-2022\\SOC\\Test\\Excel";

    @Autowired
    private FileStdService fileStdService;

    @Test
    void contextLoads() throws FileNotFoundException, IOException {
//		long begin = System.currentTimeMillis();
//
//		Workbook workbook = new SXSSFWorkbook();
//		Sheet sheet = workbook.createSheet("瞎jb测试");
//
//		for (int rowNum = 0; rowNum < 65536; rowNum++){
//			Row row = sheet.createRow(rowNum);
//			for (int cellNum = 0; cellNum < 10; cellNum++){
//				Cell cell = row.createCell(cellNum);
//				cell.setCellValue(cellNum);
//			}
//		}
//
//		FileOutputStream output = new FileOutputStream(PATH + "陈鑫扬全新版本.xlsx");
//		workbook.write(output);
//		System.out.println("表格生成成功");
//		output.close();
//		((SXSSFWorkbook) workbook).dispose();
//
//		long end = System.currentTimeMillis();
//		System.out.println((double) (end - begin) / 1000);

        FileStd fileStd = fileStdService.getFileMessageById(5, 17);
        System.out.println(fileStd);
    }

}
