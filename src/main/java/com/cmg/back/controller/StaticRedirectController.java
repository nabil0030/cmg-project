package com.cmg.back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticRedirectController {

    @GetMapping("/home")
    public String redirectToHomeHtml() {
        return "redirect:/home.html";
    }
}
