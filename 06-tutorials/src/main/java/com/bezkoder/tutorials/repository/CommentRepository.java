package com.bezkoder.tutorials.repository;

import com.bezkoder.tutorials.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTutorialId(long tutorialId);

    @Transactional
    void deleteByTutorialId(long tutorialId);
}
