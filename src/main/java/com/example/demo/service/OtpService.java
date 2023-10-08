package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class OtpService {

    private final HashMap<String, String> otps = new HashMap<>();

    private final EmailService mailSend;

    @Autowired
    public OtpService(EmailService mailSend) {
        this.mailSend = mailSend;
    }

    public String generateOtp(){

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return Integer.toString(otp);
    }

    public void setAndSendOtp(String email){
        otps.put(email, generateOtp());
        mailSend.send_otp(email, "OTP Verification", "Your verification code is:"+ otps.get(email));
    }
    public boolean verifyOtp(String email, String otp) {

        if (otp.equals(otps.get(email))){
            removeOtp(email);
            return true;
        }
        return false;

    }

    public void removeOtp(String email){
        otps.remove(email);
    }

    public void resendOtp(String email){
        removeOtp(email);
        setAndSendOtp(email);
    }




}
