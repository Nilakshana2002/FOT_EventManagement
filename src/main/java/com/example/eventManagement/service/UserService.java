package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Role;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.RoleRepository;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role (USER)
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
        user.setRoles(Collections.singleton(userRole));
        // Keep the usertype set by the controller (STUDENT or STAFF)
        if (user.getUsertype() == null) {
            user.setUsertype("STUDENT");
        }

        return userRepository.save(user);
    }

    public User saveNewUser(User user) {
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign role based on usertype
        String roleName = "USER";
        if ("ADMIN".equalsIgnoreCase(user.getUsertype())) {
            roleName = "ADMIN";
        }

        Role targetRole = roleRepository.findByName(roleName);
        if (targetRole == null) {
            targetRole = new Role();
            targetRole.setName(roleName);
            roleRepository.save(targetRole);
        }
        user.setRoles(Collections.singleton(targetRole));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByRegisterno(String registerno) {
        return userRepository.findByRegisterno(registerno);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsertype(user.getUsertype());
            existingUser.setEmail(user.getEmail());
            existingUser.setRegisterno(user.getRegisterno());
            existingUser.setPhoneno(user.getPhoneno());
            existingUser.setOrgname(user.getOrgname());

            // Only update password if a new one is provided (not empty)
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            userRepository.save(existingUser);
        }
    }
}
