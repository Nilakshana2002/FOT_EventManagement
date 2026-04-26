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


    @PostMapping("/signup")
    public String signup(User user, HttpServletRequest request, HttpServletResponse response) {


        if (user.getUsertype() != null && user.getUsertype().equals("STUDENT")) {
            if (user.getRegisterno() == null || !user.getRegisterno().matches("^(?i)[A-Z]{2}\\d{4}$")) {
                return "redirect:/?regError=true";
            }
        } else if (user.getUsertype() != null && user.getUsertype().equals("STAFF")) {
            if (user.getRegisterno() == null || !user.getRegisterno().toUpperCase().startsWith("AC")) {
                return "redirect:/?regError=true";
            }
        } else {
            return "redirect:/?regError=true";
        }


        if (user.getEmail() == null || !user.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {

            if (!user.getEmail().toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
                return "redirect:/?regError=true";
            }
        }


        User existingUserByReg = userService.findByRegisterno(user.getRegisterno());
        if (existingUserByReg != null) {
            return "redirect:/?regExists=true";
        }


        User existingUserByEmail = userService.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            return "redirect:/?exists=true";
        }


        userService.registerUser(user);

        return "redirect:/?registered=true";
    }
}