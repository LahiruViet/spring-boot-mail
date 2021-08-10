package com.example.demo.service.impl;

import com.example.demo.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("from.mail")
    private String fromMail;

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromMail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);
        log.info("Simple Mail Send...");
    }

    @Override
    public void sendEmailWithAttachment(String toEmail, String body, String subject,
                                        String attachmentPath) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem = new FileSystemResource(new File(attachmentPath));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

        javaMailSender.send(mimeMessage);
        log.info("Mail With Attachment Send...");
    }
}
