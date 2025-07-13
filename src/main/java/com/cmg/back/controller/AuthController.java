package com.cmg.back.controller;

import com.cmg.back.model.User;
import com.cmg.back.service.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ Spring's Model, not logback
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute User user, HttpSession session, Model model) {
        User found = userService.login(user.getEmail(), user.getPassword());
        if (found != null) {
            session.setAttribute("user", found);
            return "redirect:/index.html";  // ✅ exact name of static file
        }
        model.addAttribute("error", "Email or password invalid");
        return "login";
    }

}
