package com.buaa.man.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Value("${spring.mail.username}")
    private String sendUser;

    public void sendMessage(String title, String msg,
                            String receiveMail) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(sendUser);
        message.setTo("1226884767@qq.com");
        message.setSubject(title);
        message.setSentDate(new Date());
        message.setText(msg);
        mailSender.send(mimeMessage);
        message.setTo(receiveMail);
        mailSender.send(mimeMessage);
    }
}