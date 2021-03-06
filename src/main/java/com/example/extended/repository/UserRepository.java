package com.example.extended.repository;

import com.example.extended.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
