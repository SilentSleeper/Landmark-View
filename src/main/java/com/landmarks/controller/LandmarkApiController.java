package com.landmarks.controller;

import com.landmarks.domain.Comment;
import com.landmarks.domain.Landmark;
import com.landmarks.domain.User;
import com.landmarks.repository.CommentRepository;
import com.landmarks.repository.LandmarkRepository;
import com.landmarks.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/landmarks")
public class LandmarkApiController {

    private final LandmarkRepository landmarkRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public LandmarkApiController(LandmarkRepository landmarkRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.landmarkRepository = landmarkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Landmark> getAllLandmarks() {
        return landmarkRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLandmark(@PathVariable Long id) {
        Optional<Landmark> landmark = landmarkRepository.findById(id);
        if (landmark.isEmpty()) return ResponseEntity.notFound().build();

        List<Comment> comments = commentRepository.findByLandmarkOrderByCreatedAtDesc(landmark.get());
        
        Map<String, Object> response = new HashMap<>();
        response.put("landmark", landmark.get());
        response.put("comments", comments);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody Map<String, String> payload, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<Landmark> landmark = landmarkRepository.findById(id);
        if (landmark.isEmpty()) return ResponseEntity.notFound().build();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Comment comment = new Comment(payload.get("text"), user, landmark.get());
        commentRepository.save(comment);

        return ResponseEntity.ok(comment);
    }
}
