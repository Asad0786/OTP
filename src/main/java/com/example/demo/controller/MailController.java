package com.example.demo.controller;

import com.example.demo.payload.Otp;
import com.example.demo.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MailController {

    private final OtpService otpService;

    @Autowired
    public MailController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendingOtp(@RequestParam String email){

        otpService.setAndSendOtp(email);
        return ResponseEntity.ok("Sent");


    }


    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verifyingOtp(@RequestBody Otp otp){

        if(otpService.verifyOtp(otp.getEmail(), otp.getOtp()))
            return ResponseEntity.ok("Success");

        return ResponseEntity.badRequest().body("Wrong Otp");


    }


}
