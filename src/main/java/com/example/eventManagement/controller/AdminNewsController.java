package com.fot.eventsystem.controller;

import com.fot.eventsystem.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public String showNewsPage(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "admin/manage-news";
    }

    @PostMapping("/save")
    public String saveNews(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam("imageFile") MultipartFile file) {
        try {
            newsService.saveNews(title, description, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
        return "redirect:/admin/news";
    }
}