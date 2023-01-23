package com.bezkoder.tutorials.repository;

import com.bezkoder.tutorials.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
