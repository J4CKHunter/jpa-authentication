package com.erdemnayin.jpaauthentication.controller;

import com.erdemnayin.jpaauthentication.model.Post;
import com.erdemnayin.jpaauthentication.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post findPostById(@PathVariable Long id){
        return postRepository.findById(id).get();
    }
}
