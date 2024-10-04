package com.sijal.guitarshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sijal.guitarshop.entity.UserEnt; // Adjust this import to match your actual User entity package
import com.sijal.guitarshop.repository.MyUserRepository; // Import your repository properly

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fix method to correctly call findByEmail instead of findByUsername if needed
        UserEnt user = userRepository.findByUsername(email); // Assuming the email lookup method is correct
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        builder.roles("USER"); // You can customize roles here if needed

        return builder.build();
    }
}
