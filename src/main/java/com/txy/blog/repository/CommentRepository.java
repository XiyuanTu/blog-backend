package com.txy.blog.repository;

import com.txy.blog.entity.Comment;
import com.txy.blog.entity.Post;
import com.txy.blog.payload.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findCommentsByPostId(Long id);
}
