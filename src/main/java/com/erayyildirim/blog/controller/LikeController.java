package com.erayyildirim.blog.controller;


import com.erayyildirim.blog.model.Like;
import com.erayyildirim.blog.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;


    @GetMapping("")
    public ResponseEntity<List<Like>> getAllLikes(){

        List<Like> likes = likeRepository.findAllLikes();

        return ResponseEntity.ok(likes);

    }

    //like http isteğinin bodysinde gelir
    @PostMapping("")
    public ResponseEntity<Like> createLike(@Validated @RequestBody Like like){

        likeRepository.save(like);

        return ResponseEntity.ok(like);

    }
}
