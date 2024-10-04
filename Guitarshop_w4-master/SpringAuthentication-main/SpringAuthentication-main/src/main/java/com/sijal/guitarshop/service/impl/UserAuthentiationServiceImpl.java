package com.sijal.guitarshop.service.impl;


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
    MyUserRepository userRepo;

    @Override
    public boolean checkAuthDetails(UserEnt user) {
        UserEnt dbUser = userRepo.findByUsername(user.getUsername());
        if (dbUser == null) {
            return false;
        }

        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return true;
        }

        return false;
    }

    @Override
    public void saveUser(UserEnt user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
