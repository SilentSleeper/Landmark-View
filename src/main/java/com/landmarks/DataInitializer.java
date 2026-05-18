package com.landmarks;

import com.landmarks.domain.Landmark;
import com.landmarks.domain.Role;
import com.landmarks.domain.User;
import com.landmarks.repository.LandmarkRepository;
import com.landmarks.repository.RoleRepository;
import com.landmarks.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LandmarkRepository landmarkRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, LandmarkRepository landmarkRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.landmarkRepository = landmarkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            Role userRole = roleRepository.save(new Role("ROLE_USER"));

            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setDisplayName("Admin User");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
                userRepository.save(admin);

                User user = new User();
                user.setDisplayName("Regular User");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                userRepository.save(user);
            }
        }

        if (landmarkRepository.count() == 0) {
            landmarkRepository.save(new Landmark("Ateneul Român", "Concert hall, 1888", 44.4413, 26.0973));
            landmarkRepository.save(new Landmark("Palatul Parlamentului", "Worth a guided tour - the scale is unreal.", 44.4275, 26.0873));
        }
    }
}
