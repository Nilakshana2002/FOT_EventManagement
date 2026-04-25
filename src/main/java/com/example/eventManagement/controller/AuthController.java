package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.service.UserService;
import com.fot.eventsystem.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    // SIGNUP
    @PostMapping("/signup")
    public String signup(User user, HttpServletRequest request, HttpServletResponse response) {

        // 1. Validation for Registration Number based on User Type
        if (user.getUsertype() != null && user.getUsertype().equals("STUDENT")) {
            if (user.getRegisterno() == null || !user.getRegisterno().matches("^[A-Z]{2}\\d{4}$")) {
                return "redirect:/?regError=true";
            }
        } else if (user.getUsertype() != null && user.getUsertype().equals("STAFF")) {
            if (user.getRegisterno() == null || !user.getRegisterno().startsWith("AC")) {
                return "redirect:/?regError=true";
            }
        } else {
            return "redirect:/?regError=true";
        }

        // 2. Email Validation
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {
            // Using case-insensitive regex for simplicity or matching standard patterns
            if (!user.getEmail().toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
                return "redirect:/?regError=true";
            }
        }

        // 3. Check existing user (by Registration Number to prevent multiple accounts)
        User existingUserByReg = userService.findByRegisterno(user.getRegisterno());
        if (existingUserByReg != null) {
            return "redirect:/?regExists=true";
        }

        // 4. Check existing email
        User existingUserByEmail = userService.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            return "redirect:/?exists=true";
        }

        // 5. Register user
        userService.registerUser(user);

        return "redirect:/?registered=true";
    }
}