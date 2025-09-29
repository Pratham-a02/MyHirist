package com.example.linkedinProject.userService.repository;

import com.example.linkedinProject.userService.entity.User;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    boolean existsByEmail(String email);



    Optional<User> findByEmail(String email);
}
