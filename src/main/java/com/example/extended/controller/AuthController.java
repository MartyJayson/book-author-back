package com.example.extended.controller;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.example.extended.config.jwt.JwtUtils;
import com.example.extended.model.ERole;
import com.example.extended.model.Role;
import com.example.extended.model.User;
import com.example.extended.pojo.JwtResponse;
import com.example.extended.pojo.LoginRequest;
import com.example.extended.pojo.MessageResponse;
import com.example.extended.pojo.SignupRequest;
import com.example.extended.repository.RoleRepository;
import com.example.extended.repository.UserRepository;
import com.example.extended.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRespository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/{id}")
    public User find(@PathVariable Long id){
        return userRespository.findUserById(id);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody LoginRequest loginRequest){
        User user = new User();
        if(userRespository.existsByUsername(loginRequest.getUsername()))
            user = userRespository.findUserByUsername(loginRequest.getUsername());
        else if(userRespository.existsByEmail(loginRequest.getUsername()))
            user = userRespository.findUserByEmail(loginRequest.getUsername());

        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        userRespository.save(user);
        return ResponseEntity.ok("Password updated");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRespository.existsByUsername(signupRequest.getUsername()) ||
                userRespository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username or Email is already exist"));
        }
        int a = (int) (Math.random()*100000+1);
        User user = new User(
                signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                        signupRequest.getEmail(),
                        a
                );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, 30);
        user.setDate(calendar.getTime());

        List<String> reqRoles = signupRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository
                                .findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setActivated(false);
        userRespository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}