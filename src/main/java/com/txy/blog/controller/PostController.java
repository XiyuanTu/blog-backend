package com.txy.blog.controller;

import com.txy.blog.entity.Post;
import com.txy.blog.exception.BlogAPIException;
import com.txy.blog.exception.ResourceNotFoundException;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;
import com.txy.blog.service.PostService;
import com.txy.blog.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postDTO);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostPagination> getAllPosts(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = Constants.DEFAULT_IS_ASC, required = false) boolean isAsc
    ) {
        PostPagination postPagination = postService.getAllPosts(pageNo, pageSize, sortBy, isAsc);
        return new ResponseEntity<>(postPagination, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        Post post = postService.updatePostById(id, postDTO);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
