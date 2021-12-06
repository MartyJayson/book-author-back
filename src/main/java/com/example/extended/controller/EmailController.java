package com.example.extended.controller;

import com.example.extended.model.User;
import com.example.extended.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/mail")
public class EmailController {
    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public UserRepository repository;

    @GetMapping("/sendEmail")
    public String sendEmail(String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        if(repository.existsByEmail(mail)) {
            User user =repository.findUserByEmail(mail);
            message.setTo(mail);
            message.setSubject("Reset password");
            message.setText("Hello," + user.getUsername()
                    + "\nDo NOT tell this code to anyone!"
                    + "\nYour verification code: " + user.getCode()
                    + "\nIf it's wasn't you just ignore this message");

            this.mailSender.send(message);

            return "Email sent";
        }
        else return "Email doesnt registered";
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        if(repository.existsByEmail(mail)) {
            User user =repository.findUserByEmail(mail);
            message.setTo(mail);
            message.setSubject("Email verification");
            message.setText("Hello," + user.getUsername()
                    + "\nWelcome to the [website name]"
                    + "\nYour verification code: " + user.getCode()
                    + "\nIf it's wasn't you just ignore this message"
                    + "<h3><a href=\"" + "localhost:8080/verify?mail=" + user.getEmail()
                    + "&code=" + user.getCode()
                    + "\" > target=\"_self\">VERIFY</a></h3>");


            this.mailSender.send(message);

            return ResponseEntity.ok(user.getCode());
        }
        else return ResponseEntity.ok(0);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyMail(String mail, int code) {
        User user =repository.findUserByEmail(mail);
        if(code == user.getCode())
            System.out.println(user.getCode());
            user.setActivated(true);
        return ResponseEntity.ok("Account activated!");
    }
}
