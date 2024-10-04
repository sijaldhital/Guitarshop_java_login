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
@RequestMapping("/login")
public class UserAuthenticationController {

    @Autowired
    UserAuthentiationServiceImpl userAuthenticationService;

    @GetMapping
    public String logInForm(Model model){
        model.addAttribute("userLoginForm", new UserEnt());
        return "login";
    }

    @PostMapping
    public String checkLogInDetails(@ModelAttribute UserEnt user, Model model, RedirectAttributes redirectAttributes) {
        boolean checkAuthDetails = userAuthenticationService.checkAuthDetails(user);
        
        if (checkAuthDetails) { 
            redirectAttributes.addFlashAttribute("userName", user.getUsername());
            return "redirect:/user/index"; // Redirect to user home page
        }
        
        model.addAttribute("wrongCredential", "The provided credentials were wrong.");
        model.addAttribute("userLoginForm", new UserEnt());
        return "login";  
    }
}

