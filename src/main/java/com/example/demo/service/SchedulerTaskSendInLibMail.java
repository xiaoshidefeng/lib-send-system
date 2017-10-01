package com.example.demo.service;

import com.example.demo.domain.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cw on 2017/9/30.
 */
@Component
public class SchedulerTaskSendInLibMail {
    private int count = 0;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private LibSpider libSpider;

    @Scheduled(cron = "0 0 7,8,9,10,11,12,13,14,15,16,17,18 * * ?")
    private void process() {
        System.out.println("这个是定时任务第 " + (count++) + " 次执行");

        List<Subscription> list = subscriptionRepository.findBySended(false);
        if (list == null) {
            return;
        }
        for (Subscription sub:
             list) {
            String email = sub.getEmail();
            String url = sub.getUrl();

            libSpider.Get_Url(url, email);

        }
    }
}
