package com.fot.eventsystem.config;

import com.fot.eventsystem.model.Role;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.RoleRepository;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            // Create ADMIN role if not exists
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
            }

            // Create USER role if not exists
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }

            // Create default admin user if not exists
            if (userRepository.findByEmail("admin@fot.ruh.ac.lk") == null) {
                User admin = new User();
                admin.setEmail("admin@fot.ruh.ac.lk");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setUsertype("ADMIN");
                admin.setRegisterno("ADMIN001");
                admin.setRoles(Collections.singleton(adminRole));
                userRepository.save(admin);
                System.out.println("✅ Default admin user created: admin@fot.ruh.ac.lk / admin123");
            }
        };
    }
}
