package com.txy.blog.service.impl;

import com.txy.blog.entity.Category;
import com.txy.blog.entity.Post;
import com.txy.blog.exception.ResourceNotFoundException;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;
import com.txy.blog.repository.PostRepository;
import com.txy.blog.service.CategoryService;
import com.txy.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        Long categoryId = postDTO.getCategoryId();
        Category category = categoryService.getCategoryById(categoryId);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return newPost;
    }

    @Override
    public PostPagination getAllPosts(int pageNo, int pageSize, String sortBy, boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();
        List<PostDTO> postDTOs = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostPagination postPagination = new PostPagination();
        postPagination.setContent(postDTOs);
        postPagination.setPageNo(pageNo);
        postPagination.setPageSize(pageSize);
        postPagination.setTotalPosts(page.getTotalElements());
        postPagination.setTotalPages(page.getTotalPages());
        postPagination.setLastPage(page.isLast());

        return postPagination;
    }


    @Override
    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        return post;
    }

    @Override
    public Post updatePostById(Long id, PostDTO postDTO) {
        Post post = getPostById(id);

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        Long categoryId = postDTO.getCategoryId();
        Category category = categoryService.getCategoryById(categoryId);
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);

        return updatedPost;
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }

    @Override
    public List<Post> getPostsByCategoryId(Long categoryId) {
        categoryService.getCategoryById(categoryId);
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts;
    }
}
