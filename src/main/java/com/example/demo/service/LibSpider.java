package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.util.MailUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cw on 2017/9/30.
 */
@Component
public class LibSpider {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void Get_Url(String url, String email) {

        try {
            Boolean is_inLib = false;
            String[] book_info = new String[7];
            int count = 0;

            //Jsoup来提取图书馆藏信息
            Document doc = Jsoup.connect(url)
                    //.data("query", "Java")
                    .userAgent("'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '                            'Chrome/51.0.2704.63 Safari/537.36'")
                    //.cookie("auth", "token")
                    .timeout(10000)
                    //.post()
                    .get();

            Elements elements = doc.select(".whitetext td");
            for (Element ele : elements) {
                String text = ele.text();               //获取当前标签（元素）的文本值

                if (count < 5) {
                    book_info[count] = text;
                    count ++;
                }
                if (text.equals("可借")) {
                    is_inLib = true;
                }
            }

            if (is_inLib) {
                //正则表达式找出书名
                String result = "";
                Pattern pattern = Pattern.compile("document.title = \"(.*?)\";");
                Matcher matcher = pattern.matcher(doc.toString());
                while (matcher.find()) {
                    System.out.println(matcher.group(1));
                    Book book = new Book(book_info[0], "《" + matcher.group(1) + "》", book_info[1], book_info[3]);
                    if (mailUtil.sendGetBookMail(email, book)) {
                        List<Subscription> list = subscriptionRepository.findByEmailAndAndUrlAndAndSended(email, url, false);
                        for (Subscription sub:
                                list) {
                            sub.setSended(true);
                            subscriptionRepository.save(sub);
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
