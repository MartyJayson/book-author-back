package com.example.extended.service;

import com.example.extended.dto.UserDTO;
import com.example.extended.model.Role;
import com.example.extended.model.User;
import com.example.extended.repository.RoleRepository;
import com.example.extended.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDTO userDto){
        Role roleUser = roleRepository.findByName("USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userRoles);
        return repository.save(user);
    }

    public List<User> getAll(){
        return repository.findAll();
    }
    public User findByUsername(String username){
        return repository.findUserByUsername(username);
    }
    public User findById(Long id){
        return repository.findUserById(id);
    }
    public void delete(Long id){
        repository.deleteById(id);
    }
    public String login(String username, String password){
        User user = repository.findUserByUsername(username);
        if(user.getPassword().equals(passwordEncoder.encode(password)))
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
