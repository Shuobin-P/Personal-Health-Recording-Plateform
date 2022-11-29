package com.google.personalhealthrecordingplateform.util;

import com.google.personalhealthrecordingplateform.vo.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/25 22:24
 */
@Slf4j
@Component
public class EmailUtils {
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public EmailUtils(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送邮件
     *
     * @param mail 发送内容
     */
    public void sendMail(MailVO mail) throws MessagingException {
        if (mail.getHtml()) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(mail.getReceivers());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getContent(), true);
            mailSender.send(mimeMessage);
            log.info("HTML邮件发送成功！收件人---{}---", Arrays.asList(mail.getReceivers()));
        } else {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(from);
            mailMessage.setTo(mail.getReceivers());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getContent());
            System.out.println(mailSender.getHost());
            mailSender.send(mailMessage);
            log.info("普通邮件发送成功! 收件人---{}---", Arrays.asList(mail.getReceivers()));
        }

    }
}
