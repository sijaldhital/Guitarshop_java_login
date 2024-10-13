package com.sijal.guitarshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sijal.guitarshop.entity.UserEnt;
import com.sijal.guitarshop.repository.MyUserRepository;

@Service
public class UserAuthentiationServiceImpl implements UserAuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserRepository userRepo;

    // Authenticate user details
    @Override
    public boolean checkAuthDetails(UserEnt user) {
        UserEnt dbUser = userRepo.findByUsername(user.getUsername());
        if (dbUser == null) {
            return false;
        }

        return passwordEncoder.matches(user.getPassword(), dbUser.getPassword());
    }

    // Create or save a user
    @Override
    public void saveUser(UserEnt user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    // Retrieve all users
    public List<UserEnt> getAllUsers() {
        return userRepo.findAll();
    }

    // Retrieve a user by ID
    public Optional<UserEnt> getUserById(Long id) {
        return userRepo.findById(id);
    }

    // Update an existing user
    public UserEnt updateUser(Long id, UserEnt updatedUser) {
        Optional<UserEnt> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            UserEnt user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            // Encode the new password if updated
            if (!updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepo.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
