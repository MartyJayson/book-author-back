package com.example.extended.controller;

import com.example.extended.model.User;
import com.example.extended.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


@Controller
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*")
public class EmailController {
    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public UserRepository repository;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        if(repository.existsByEmail(mail)) {
            User user =repository.findUserByEmail(mail);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            calendar.add(Calendar.MINUTE, 30);
            user.setDate(calendar.getTime());
            repository.save(user);
            String link = "http://localhost:8080/api/mail/verify?mail=" + user.getEmail()
                    + "&code=" + user.getCode();
            message.setTo(mail);
            message.setSubject("Reset password");
            message.setText("Hello," + user.getUsername()
                    + "\nDo NOT tell this code to anyone!"
                    + "\nYour verification code: " + user.getCode()
                    + "\nOr just go throw the link:"
                    + link
                    + "\nIf it's wasn't you just ignore this message"
            );
            this.mailSender.send(message);

            return String.valueOf(user.getCode());
        }
        else return "Email doesn't registered";
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(@RequestParam String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        if(repository.existsByEmail(mail)) {
            User user = repository.findUserByEmail(mail);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            calendar.add(Calendar.MINUTE, 30);
            user.setDate(calendar.getTime());
            repository.save(user);
            String link = "http://localhost:8080/api/mail/verify?mail=" + user.getEmail()
                    + "&code=" + user.getCode();
            message.setTo(mail);
            message.setSubject("Email verification");
            message.setText("Hello," + user.getUsername()
                    + "\nWelcome to the [website name]"
                    + "\nPlease, go throw the link:\n"
                    + link
                    );

            this.mailSender.send(message);

            return ResponseEntity.ok(1);

            }
        return ResponseEntity.ok(0);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String mail, @RequestParam int code) {
        User user =repository.findUserByEmail(mail);
        Date now = new Date();
        if(code == user.getCode() && user.getDate().after(now))
        {
            System.out.println(user.getCode() + " " + code);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);

    }
    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyMail(@RequestParam String mail, @RequestParam int code) {
        User user =repository.findUserByEmail(mail);
        if(code == user.getCode())
        {
            int a = (int) (Math.random()*100000+1);

            System.out.println(user.getCode() + " " + code);
            user.setActivated(true);
            user.setCode(a);
            repository.save(user);
            return ResponseEntity.ok("Account activated!");
        }
        return ResponseEntity.ok("Account is already activated or incorrect verification code!");

    }
}
