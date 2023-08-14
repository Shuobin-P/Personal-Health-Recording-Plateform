package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/26 20:39
 */
public class MailTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void test() {
        String to = "391722449@qq.com";
        // 发件人电子邮箱
        String from = "3120465015@qq.com";
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("3120465015@qq.com", "namlndaeqcdqdceh"); //发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

            // 设置消息体
            message.setText("测试信息");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    @Test
    public void test1() {
        this.sendMail();
    }

    public void sendMail() {
        System.out.println(mailSender == null);
        System.out.println(mailSender.getHost());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("admin@163.com");
        simpleMailMessage.setTo("socks@qq.com");
        simpleMailMessage.setSubject("Happy New Year");
        simpleMailMessage.setText("新年快乐！");
        mailSender.send(simpleMailMessage);
    }
}
