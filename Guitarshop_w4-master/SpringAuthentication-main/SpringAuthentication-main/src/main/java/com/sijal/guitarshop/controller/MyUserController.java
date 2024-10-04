package com.sijal.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijal.guitarshop.entity.UserEnt;
import com.sijal.guitarshop.service.impl.UserAuthentiationServiceImpl;

@Controller
@RequestMapping("/")
public class MyUserController {

    @Autowired
    UserAuthentiationServiceImpl userAuthenticationService;

    @GetMapping("/")
    public String indexPage() {
        return "home"; // Ensure you have home.html in your templates folder
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRegisterForm", new UserEnt());
        return "register"; // Ensure you have register.html in your templates folder
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserEnt user, RedirectAttributes redirectAttributes) {
        try {
            userAuthenticationService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login"; // Ensure you have a login page at /login
        } catch (Exception e) {
            // Handle any exceptions during user registration, e.g., duplicate username
            redirectAttributes.addFlashAttribute("errorMessage", "Registration failed. Please try again.");
            return "redirect:/register"; // Redirect back to the registration page
        }
    }   
}
