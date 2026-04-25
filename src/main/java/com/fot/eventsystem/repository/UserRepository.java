package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//bridge between java and db
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User findByRegisterno(String registerno);
}