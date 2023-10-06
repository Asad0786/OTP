package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class OtpService {

    private final HashMap<String, String> otps = new HashMap<>();

    private final MailSend mailSend;

    @Autowired
    public OtpService(MailSend mailSend) {
        this.mailSend = mailSend;
    }

    public String generateOtp(){

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return Integer.toString(otp);
    }

    public void setAndSendOtp(String email){
        otps.put(email, generateOtp());
        String subject = "OTP Verification";
        String message = "Your verification code is:"+ otps.get(email);
        mailSend.send_otp(email, subject, message);
    }
    public boolean verifyOtp(String email, String otp) {

        System.out.println(otp);
        System.out.println(otps.get(email));
        if (otp.equals(otps.get(email))){
            removeOtp(email);
            return true;
        }
        return false;

    }

    public void removeOtp(String email){
        otps.remove(email);
    }




}
