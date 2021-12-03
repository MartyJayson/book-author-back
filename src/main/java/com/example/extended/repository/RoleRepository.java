package com.example.extended.repository;

import com.example.extended.model.ERole;
import com.example.extended.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Optional<Role> findByName(ERole name);
}
