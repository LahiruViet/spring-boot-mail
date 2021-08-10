package com.example.demo;

import com.example.demo.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private EmailSenderService emailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {

		emailSenderService.sendSimpleEmail("email.to@gmail.com",
				"This is Email Body ...",
				"This is email Subject ...");

		emailSenderService.sendEmailWithAttachment("email.to@gmail.com",
				"This is Email Body with Attachment...",
				"This email has attachment",
				"C:\\hello.gif");

	}
}
