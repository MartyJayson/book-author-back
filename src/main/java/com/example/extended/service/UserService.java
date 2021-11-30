package com.example.extended.service;

import com.example.extended.dto.UserDTO;
import com.example.extended.model.User;
import com.example.extended.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Formatter;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;

    public User registerNewUserAccount(UserDTO userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setRoles(userDto.getRoles());

        return repository.save(user);
    }
    public String login(String username, String password){
        User user = repository.findUserByUsername(username);
        if(user.getPassword().equals(encryptPassword(password)))
        {
          //  System.out.println(user.getPassword() + " " + encryptPassword(password));
            AuthenticationBean authenticationBean = new AuthenticationBean(username);
            return "Hello" + authenticationBean.getMessage();
        }
        else
            return "Incorrect password";
    }

    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
