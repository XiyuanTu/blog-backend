package com.txy.blog.service.impl;

import com.txy.blog.entity.Comment;
import com.txy.blog.entity.Post;
import com.txy.blog.exception.BlogAPIException;
import com.txy.blog.exception.ResourceNotFoundException;
import com.txy.blog.payload.CommentDTO;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.repository.CommentRepository;
import com.txy.blog.repository.PostRepository;
import com.txy.blog.service.CommentService;
import com.txy.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostService postService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment createComment(Long postId, CommentDTO commentDTO) {
        Post post = postService.getPostById(postId);
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return newComment;
    }

    @Override
    public List<Comment> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        return comments;
    }

    @Override
    public Comment getCommentById(Long postId, Long id) {
        postService.getPostById(postId);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException("Comment does not belong to post");
        }

        return comment;
    }

    @Override
    public Comment updateCommentById(Long postId, Long id, CommentDTO commentDTO) {
        Comment comment = getCommentById(postId, id);
        comment.setContent(commentDTO.getContent());
        comment.setEmail(commentDTO.getEmail());
        comment.setUsername(commentDTO.getUsername());
        Comment updatedComment = commentRepository.save(comment);
        return updatedComment;
    }

    @Override
    public void deleteCommentById(Long postId, Long id) {
        Comment comment = getCommentById(postId, id);
        commentRepository.delete(comment);
    }
}
