package com.txy.blog.controller;

import com.txy.blog.entity.Comment;
import com.txy.blog.payload.CommentDTO;
import com.txy.blog.service.CommentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(postId, commentDTO);
        CommentDTO commentDTOResponse = modelMapper.map(comment, CommentDTO.class);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getAllComments(postId);
        List<CommentDTO> commentDTOsResponse = comments.stream().map(comment -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOsResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postId, @PathVariable Long id) {
        Comment comment = commentService.getCommentById(postId, id);
        System.out.println(comment);
        CommentDTO commentDTOResponse = modelMapper.map(comment, CommentDTO.class);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable Long postId, @PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.updateCommentById(postId, id, commentDTO);
        CommentDTO commentDTOResponse = modelMapper.map(comment, CommentDTO.class);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId, @PathVariable Long id) {
        commentService.deleteCommentById(postId, id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
