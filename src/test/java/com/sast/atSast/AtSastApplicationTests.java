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
		List<Long> teamIds = new ArrayList<>();
		long i = 1;
		while (i <= 10){
			teamIds.add(i);
			i++;
		}
		System.out.println(teamIds);
	}

}
