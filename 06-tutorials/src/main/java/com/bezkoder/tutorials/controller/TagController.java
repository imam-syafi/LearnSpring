package com.bezkoder.tutorials.controller;

import com.bezkoder.tutorials.exception.ResourceNotFoundException;
import com.bezkoder.tutorials.model.Tag;
import com.bezkoder.tutorials.repository.TagRepository;
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
public class TagController {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TagRepository tagRepository;

    @PostMapping("tutorials/{tutorialId}/tags")
    public ResponseEntity<Tag> addTag(@PathVariable(value = "tutorialId") Long tutorialId,
                                      @RequestBody Tag tagPost) {
        Tag tag = tutorialRepository.findById(tutorialId)
                .map(tutorial -> {
                    long tagId = tagPost.getId();

                    if (tagId != 0L) {
                        return tagRepository.findById(tagId)
                                .map(existingTag -> {
                                    tutorial.addTag(existingTag);
                                    tutorialRepository.save(tutorial);
                                    return existingTag;
                                })
                                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
                    }

                    // add and create new Tag
                    tutorial.addTag(tagPost);
                    return tagRepository.save(tagPost);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }
}
