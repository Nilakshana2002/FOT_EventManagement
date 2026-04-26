package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/manage-users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        try {

            String reg = user.getRegisterno();
            String type = user.getUsertype();

            if ("STUDENT".equals(type)) {
                if (reg == null || !reg.matches("^[A-Z]{2}\\d{4}$") || !reg.startsWith("TG")) {
                    return "redirect:/admin/users?error=invalidFormat&type=STUDENT";
                }
            } else if ("STAFF".equals(type) || "ADMIN".equals(type)) {
                if (reg == null || !reg.startsWith("AC")) {
                    return "redirect:/admin/users?error=invalidFormat&type=STAFF";
                }
            }


            if (userService.findByRegisterno(user.getRegisterno()) != null) {
                return "redirect:/admin/users?error=exists";
            }
            
            userService.saveNewUser(user);
            return "redirect:/admin/users?success=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/users?error=true";
        }
    }
}
