package com.erdemnayin.jpaauthentication.repository;

import com.erdemnayin.jpaauthentication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
