package com.example.demo.service;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.AccountApi;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Value("${sbin.key}")
    private String key;


    public void send_otp(String recipientEmail, String subject, String content) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(key);
        AccountApi apiInstance = new AccountApi();

        try {

            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmail sendSmtpEmail = getSendSmtpEmail(recipientEmail, subject, content);
            api.sendTransacEmail(sendSmtpEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred:- " + e.getMessage());
        }
    }

    @Value("${sbin.username}")
    private String senderEmail;


    @NotNull
    private SendSmtpEmail getSendSmtpEmail(String recipientEmail, String subject, String content) {

        //Sender
        SendSmtpEmailSender sendSmtpEmailSender = new SendSmtpEmailSender();
        sendSmtpEmailSender.setEmail(senderEmail);
        sendSmtpEmailSender.setName("Enter Your name here");


        //Receivers
        SendSmtpEmailTo sendSmtpEmailTo = new SendSmtpEmailTo();
        sendSmtpEmailTo.setEmail(recipientEmail);
        sendSmtpEmailTo.setName(recipientEmail);


        List<SendSmtpEmailTo> to = new ArrayList<>();
        to.add(sendSmtpEmailTo);



        // Config for mail
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        sendSmtpEmail.setSender(sendSmtpEmailSender);
        sendSmtpEmail.setTextContent(content);
        sendSmtpEmail.setSender(sendSmtpEmailSender);
        sendSmtpEmail.setSubject(subject);
        sendSmtpEmail.setTo(to);

        return sendSmtpEmail;
    }


}