package com.landmarks.repository;

import com.landmarks.domain.Comment;
import com.landmarks.domain.Landmark;
import com.landmarks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLandmarkOrderByCreatedAtDesc(Landmark landmark);
    List<Comment> findByUserOrderByCreatedAtDesc(User user);
}
