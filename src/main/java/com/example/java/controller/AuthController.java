package com.example.java.controller;


import com.example.java.model.Usuario;
import com.example.java.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;


    @GetMapping("/")
    public String redirectToMainPage(){
        return "redirect:/plantas";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registro")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registerUser(Usuario user){

        return "redirect:/plantas";
    }
}
