package com.temperatureFinder.repository;

import com.temperatureFinder.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
