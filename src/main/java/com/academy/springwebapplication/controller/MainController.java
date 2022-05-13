package com.academy.springwebapplication.controller;

import com.academy.springwebapplication.model.entity.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("route",new Route());

        return "main";
    }
}