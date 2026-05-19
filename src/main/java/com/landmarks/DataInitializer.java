package com.landmarks;

import com.landmarks.domain.Comment;
import com.landmarks.domain.Landmark;
import com.landmarks.domain.Role;
import com.landmarks.domain.User;
import com.landmarks.repository.CommentRepository;
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
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, LandmarkRepository landmarkRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.landmarkRepository = landmarkRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        User admin = userRepository.findByEmail("admin@example.com").orElseGet(() -> {
            User a = new User();
            a.setDisplayName("Admin User");
            a.setEmail("admin@example.com");
            a.setPassword(passwordEncoder.encode("admin"));
            a.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
            return userRepository.save(a);
        });

        User user = userRepository.findByEmail("user@example.com").orElseGet(() -> {
            User u = new User();
            u.setDisplayName("Andrei Popa");
            u.setEmail("user@example.com");
            u.setPassword(passwordEncoder.encode("password"));
            u.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            return userRepository.save(u);
        });

        if (landmarkRepository.count() == 0) {
            Landmark ateneu = landmarkRepository.save(new Landmark("Ateneul Român", "The Romanian Athenaeum is a concert hall in the center of Bucharest, Romania, and a landmark of the Romanian capital city.", 44.4413, 26.0973, "Palace", "https://images.unsplash.com/photo-1584897262100-2f9543888365?auto=format&fit=crop&w=800&q=80"));
            Landmark palat = landmarkRepository.save(new Landmark("Palatul Parlamentului", "The Palace of the Parliament is the seat of the Parliament of Romania. It is the second largest administrative building in the world.", 44.4275, 26.0873, "Monument", "https://images.unsplash.com/photo-1627834571988-12e098be9036?auto=format&fit=crop&w=800&q=80"));
            Landmark arc = landmarkRepository.save(new Landmark("Arcul de Triumf", "A triumphal arch located in the northern part of Bucharest, on the Kiseleff Road.", 44.4672, 26.0782, "Monument", "https://images.unsplash.com/photo-1596489370605-7264a75abf1d?auto=format&fit=crop&w=800&q=80"));
            Landmark muzeu = landmarkRepository.save(new Landmark("Muzeul Național de Artă", "The National Museum of Art of Romania is located in the former royal palace in Revolution Square.", 44.4397, 26.0958, "Museum", "https://images.unsplash.com/photo-1580227318760-7c27156942c4?auto=format&fit=crop&w=800&q=80"));
            Landmark biserica = landmarkRepository.save(new Landmark("Biserica Stavropoleos", "Stavropoleos Monastery, also known as Stavropoleos Church during the last century when the monastery was dissolved.", 44.4318, 26.0987, "Church", "https://images.unsplash.com/photo-1595155890886-c30f40fb1713?auto=format&fit=crop&w=800&q=80"));

            if (commentRepository.count() == 0) {
                commentRepository.save(new Comment("Best acoustics in the city. Don't miss a concert here.", user, ateneu));
                commentRepository.save(new Comment("Visited on a Sunday morning — almost empty, peaceful.", admin, ateneu));
                commentRepository.save(new Comment("Worth a guided tour — the scale is unreal.", user, palat));
                commentRepository.save(new Comment("Beautiful architecture!", admin, arc));
            }
        }
    }
}
