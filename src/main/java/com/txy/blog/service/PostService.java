package com.txy.blog.service;

import com.txy.blog.entity.Post;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;


public interface PostService {
    Post createPost(PostDTO postDTO);

    PostPagination getAllPosts(int pageNo, int pageSize, String sortBy, boolean isAsc);

    Post getPostById(Long id);

    Post updatePostById(Long id, PostDTO postDTO);

    Post updatePost(Post post);

    void deletePostById(Long id);
}
