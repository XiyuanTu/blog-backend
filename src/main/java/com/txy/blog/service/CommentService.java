package com.txy.blog.service;


import com.txy.blog.entity.Comment;
import com.txy.blog.payload.CommentDTO;
import com.txy.blog.payload.PostDTO;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, CommentDTO commentDTO);

    List<Comment> getAllComments(Long postId);

    Comment getCommentById(Long postId, Long id);

    Comment updateCommentById(Long postId, Long id, CommentDTO commentDTO);

    void deleteCommentById(Long postId, Long id);
}
