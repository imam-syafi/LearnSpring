package com.bezkoder.tutorials.repository;

import com.bezkoder.tutorials.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
