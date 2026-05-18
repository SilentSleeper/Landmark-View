package com.landmarks.service;

import com.landmarks.controller.UserRegistrationDto;
import com.landmarks.domain.Role;
import com.landmarks.domain.User;
import com.landmarks.repository.RoleRepository;
import com.landmarks.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUserAccount(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("There is an account with that email address: " + registrationDto.getEmail());
        }

        User user = new User();
        user.setDisplayName(registrationDto.getDisplayName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found."));

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }
}
