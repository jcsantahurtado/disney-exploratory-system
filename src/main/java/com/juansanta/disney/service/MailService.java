package com.juansanta.disney.service;

import java.io.IOException;

import com.juansanta.disney.dto.EmailToSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Async
    public String sendTextEmail(EmailToSend emailToSend) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Email from = new Email(emailToSend.getFrom());  // sender email
        String subject = emailToSend.getSubject();
        Email to = new Email(emailToSend.getTo());  // the reciver
        Content content = new Content("text/plain", emailToSend.getContent());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("SENDGRID_API_KEY");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
