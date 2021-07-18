package com.sast.atSast;

import com.alibaba.fastjson.JSON;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.service.impl.ContestServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AtSastApplicationTests {

	@Autowired
	private ContestServiceImpl contestService;

	@Test
	void contextLoads() {
//		String data = "[\n" +
//				"    {\n" +
//				"        \"stageBegin\": \"2013-03-06 17:17:16\",\n" +
//				"        \"stageEnd\": \"1974-09-08 01:06:24\",\n" +
//				"        \"stageId\": 38,\n" +
//				"        \"contestId\": 15,\n" +
//				"        \"stageName\": \"秀英\",\n" +
//				"        \"stageType\": \"non ipsum\"\n" +
//				"    }\n" +
//				"]";
	}

}
