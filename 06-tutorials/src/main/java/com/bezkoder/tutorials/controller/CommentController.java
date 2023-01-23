package com.bezkoder.tutorials.controller;

import com.bezkoder.tutorials.exception.ResourceNotFoundException;
import com.bezkoder.tutorials.model.Comment;
import com.bezkoder.tutorials.repository.CommentRepository;
import com.bezkoder.tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") Long tutorialId,
                                                 @RequestBody Comment commentPost) {
        return tutorialRepository.findById(tutorialId)
                .map(tutorial -> {
                    commentPost.setTutorial(tutorial);
                    return new ResponseEntity<>(commentRepository.save(commentPost), HttpStatus.CREATED);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
    }
}
