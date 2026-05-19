package com.landmarks.controller;

import com.landmarks.domain.Comment;
import com.landmarks.domain.User;
import com.landmarks.repository.CommentRepository;
import com.landmarks.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ProfileController(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
                List<Comment> comments = commentRepository.findByUserOrderByCreatedAtDesc(user);
                model.addAttribute("comments", comments);
            }
        }
        return "profile";
    }
}
