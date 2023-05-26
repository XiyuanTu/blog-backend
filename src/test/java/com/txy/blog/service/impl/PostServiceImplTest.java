package com.txy.blog.service.impl;

import com.txy.blog.entity.Category;
import com.txy.blog.entity.Post;
import com.txy.blog.exception.ResourceNotFoundException;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;
import com.txy.blog.repository.PostRepository;
import com.txy.blog.service.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@TestPropertySource("/application.yml")
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryService categoryService;

    private Post post;
    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .title("test")
                .content("no content")
                .description("test")
                .category(Category.builder().build())
                .build();

        postDTO = PostDTO.builder()
                .title("test")
                .content("no content")
                .description("test")
                .categoryId(1L)
                .comments(new HashSet<>())
                .build();
    }

    @Test
    void createPost_ReturnPost() {
        when(modelMapper.map(any(PostDTO.class), eq(Post.class))).thenReturn(post);
        when(categoryService.getCategoryById(any())).thenReturn(mock(Category.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post newPost = postService.createPost(postDTO);

        Assertions.assertThat(newPost).isNotNull();
        Assertions.assertThat(newPost.getTitle()).isEqualTo("test");
    }

    @Test
    void getAllPosts_ReturnPostPagination() {
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));

        PostPagination postPagination = postService.getAllPosts(1, 2, "id", true);

        Assertions.assertThat(postPagination).isNotNull();
        Assertions.assertThat(postPagination.getPageNo()).isEqualTo(1);
    }

    @Test
    void getPostById_ReturnPost() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        Post retrivedPost = postService.getPostById(anyLong());

        Assertions.assertThat(retrivedPost).isNotNull();
        Assertions.assertThat(retrivedPost.getTitle()).isEqualTo("test");
    }

    @Test
    void getPostById_ThrowResourceNotFoundException() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> postService.getPostById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: '1'");
    }

    @Test
    void updatePostById_ReturnPost() {
        Post updatedPost = Post.builder()
                .id(1L)
                .title("test")
                .content("no content (updated)")
                .description("test")
                .category(Category.builder().build())
                .build();

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post updatedPostToTest = postService.updatePostById(1L, mock(PostDTO.class));

        Assertions.assertThat(updatedPostToTest).isNotNull();
        Assertions.assertThat(updatedPostToTest.getContent()).isEqualTo(updatedPost.getContent());

    }

    @Test
    void deletePostById_ReturnVoid() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(any(Post.class));

        postService.deletePostById(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void getPostsByCategoryId_ReturnPosts() {
        when(postRepository.findByCategoryId(anyLong())).thenReturn(mock(List.class));
        List<Post> posts = postService.getPostsByCategoryId(anyLong());

        Assertions.assertThat(posts).isNotNull();
    }
}