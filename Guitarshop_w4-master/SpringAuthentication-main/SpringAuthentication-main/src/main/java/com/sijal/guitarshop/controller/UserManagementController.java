package com.sijal.guitarshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sijal.guitarshop.entity.UserEnt;
import com.sijal.guitarshop.service.impl.UserAuthentiationServiceImpl;

@Controller
@RequestMapping("/users")
public class UserManagementController {

    @Autowired
    private UserAuthentiationServiceImpl userService;

    // Display all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    // Show form to create a new user
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserEnt());
        return "user-form";
    }

    // Save a new user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserEnt user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // Show form to edit an existing user
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<UserEnt> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "user-form";
        } else {
            return "error";
        }
    }

    // Update user details
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserEnt user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
