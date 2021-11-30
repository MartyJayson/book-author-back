package com.example.extended.controller;

import com.example.extended.dto.UserDTO;
import com.example.extended.service.AuthenticationBean;
import com.example.extended.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/basicauth")
    public AuthenticationBean auth(){
        return new AuthenticationBean("You are authenticated");
    }
    @PostMapping("/registration")
    public String registration(@RequestBody UserDTO user){
        userService.registerNewUserAccount(user);
        return "User successfully registered!";
    }
    @PostMapping("/login")
    public String login(@RequestBody UserDTO user, HttpSession httpSession){
        System.out.println(httpSession.getId() + " " + httpSession.getLastAccessedTime());
        return userService.login(user.getUsername(), user.getPassword());
    }
}
