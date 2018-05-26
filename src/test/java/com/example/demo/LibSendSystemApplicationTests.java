package com.example.demo;

import com.example.demo.service.LibSpider;
import com.example.demo.service.SchedulerTaskSendInLibMail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibSendSystemApplicationTests {

	@Autowired
	private LibSpider libSpider;

	private SchedulerTaskSendInLibMail schedulerTaskSendInLibMail;


	@Test
	public void contextLoads() {
//		String url = "http://my.lib.lsu.edu.cn/opac/item.php?marc_no=0000655729";
//		libSpider.Get_Url(url, "1330661071@qq.com");

	}

}

