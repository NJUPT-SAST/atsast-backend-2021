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
//		String data = "{\n" +
//				"    \"masterUid\": 9,\n" +
//				"    \"contestName\": \"敏\",\n" +
//				"    \"description\": \"集前务青到为热消深清精立能加律住。要周知多理内民加党适参连五会调相白。这低世音量外别到天达声毛。决极一心热知争全治太且没直油。听新化同是五到当设金来电取。\",\n" +
//				"    \"contestOrganizer\": \"娜\",\n" +
//				"    \"contestHost\": \"秀兰\",\n" +
//				"    \"contestHelper\": \"艳\",\n" +
//				"    \"isTeam\": true,\n" +
//				"    \"isInstructor\": true,\n" +
//				"    \"banners\": \"deserunt sunt\",\n" +
//				"    \"teamGroup\": \"anim cupidatat labore pariatur\",\n" +
//				"    \"subjectCategory\": \"ut Duis\",\n" +
//				"    \"workCategory\": \"incididunt\",\n" +
//				"    \"joinGrade\": \"irure ullamco\",\n" +
//				"    \"isJoin\": true,\n" +
//				"    \"stages\": [\n" +
//				"        {\n" +
//				"            \"stageBegin\": \"1975-12-21 09:23:34\",\n" +
//				"            \"stageEnd\": \"2004-11-28 20:10:17\",\n" +
//				"            \"stageId\": 25,\n" +
//				"            \"contestId\": 19,\n" +
//				"            \"stageName\": \"超\",\n" +
//				"            \"stageType\": \"deserunt officia ad amet et\"\n" +
//				"        },\n" +
//				"        {\n" +
//				"            \"stageBegin\": \"2005-10-23 10:26:28\",\n" +
//				"            \"stageEnd\": \"1970-09-26 03:33:03\",\n" +
//				"            \"stageId\": 79,\n" +
//				"            \"contestId\": 97,\n" +
//				"            \"stageName\": \"强\",\n" +
//				"            \"stageType\": \"minim fugiat\"\n" +
//				"        }\n" +
//				"    ],\n" +
//				"    \"minMember\": 30,\n" +
//				"    \"maxMember\": 78,\n" +
//				"    \"minInstructor\": 18,\n" +
//				"    \"maxInstructor\": 10,\n" +
//				"    \"contestType\": 74\n" +
//				"}";
//		Contest contest = JSON.parseObject(data, Contest.class);
//		List<Stage> stages = JSON.parseArray(contest.getStages(), Stage.class);
//		contestService.createContest(contest);
//		for (Stage stage : stages){
//			contestService.createStage(stage);
//		}

//		List<String> data = getTeamById(44);
	}

}
