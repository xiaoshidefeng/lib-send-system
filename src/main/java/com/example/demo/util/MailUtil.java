package com.example.demo.util;

import com.example.demo.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Created by cw on 2017/7/19.
 */
@Component
public class MailUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;


    public boolean sendGetBookMail(String email, Book book) {
        MimeMessage message = mailSender.createMimeMessage();

        //创建邮件正文
        Context context = new Context();
        context.setVariable("book_id", book.getBook_id());
        context.setVariable("book_name", book.getBook_name());
        context.setVariable("book_code", book.getBook_code());
        context.setVariable("location", book.getLocation());
        context.setVariable("time", Tool.getTime());

        String emailContent = templateEngine.process("InLibMail", context);
        String sub = "图书到馆通知邮件";

        System.out.println("《" + book.getBook_name() + "》" + "已到馆，正在发送邮件通知");

        return tosend(message, email, emailContent, sub);
    }

    private boolean tosend(MimeMessage message, String email, String emailContent, String sub) {
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(sub);
            helper.setText(emailContent, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
            return true;
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
            return false;
        }
    }

}
