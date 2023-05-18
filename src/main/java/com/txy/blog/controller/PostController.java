package com.txy.blog.controller;

import com.txy.blog.entity.Post;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;
import com.txy.blog.service.PostService;
import com.txy.blog.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create Post")
    @ApiResponse(responseCode = "201", description = "Post created")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postDTO);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping("/v1/posts")
    @Operation(summary = "Get All Posts")
    @ApiResponse(responseCode = "200", description = "Posts fetched")
    public ResponseEntity<PostPagination> getAllPosts(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = Constants.DEFAULT_IS_ASC, required = false) boolean isAsc
    ) {
        PostPagination postPagination = postService.getAllPosts(pageNo, pageSize, sortBy, isAsc);
        return new ResponseEntity<>(postPagination, HttpStatus.OK);
    }

    @GetMapping("/v1/posts/{id}")
    @Operation(summary = "Get Post By Id")
    @ApiResponse(responseCode = "200", description = "Post fetched")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/posts/{id}")
    @Operation(summary = "Update Post By Id")
    @ApiResponse(responseCode = "200", description = "Post updated")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) {
        Post post = postService.updatePostById(id, postDTO);
        PostDTO postDTOResponse = modelMapper.map(post, PostDTO.class);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/v1/posts/{id}")
    @Operation(summary = "Delete Post By Id")
    @ApiResponse(responseCode = "200", description = "Post deleted")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/v1/posts/category/{id}")
    @Operation(summary = "Get Posts By Category Id")
    @ApiResponse(responseCode = "200", description = "Post fetched")
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable Long id) {
        List<Post> posts = postService.getPostsByCategoryId(id);
        List<PostDTO> postDTOS = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }
}
