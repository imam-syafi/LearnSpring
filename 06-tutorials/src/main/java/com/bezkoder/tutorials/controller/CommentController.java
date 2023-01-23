package com.bezkoder.tutorials.controller;

import com.bezkoder.tutorials.exception.ResourceNotFoundException;
import com.bezkoder.tutorials.model.Comment;
import com.bezkoder.tutorials.repository.CommentRepository;
import com.bezkoder.tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        List<Comment> comments = commentRepository.findByTutorialId(tutorialId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        commentRepository.deleteByTutorialId(tutorialId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long id) {
        return commentRepository.findById(id)
                .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") long id,
                                                 @RequestBody Comment commentPut) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(commentPut.getContent());
                    return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
