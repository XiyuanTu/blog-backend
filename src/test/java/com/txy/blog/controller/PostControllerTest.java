package com.txy.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txy.blog.entity.Category;
import com.txy.blog.entity.Post;
import com.txy.blog.payload.PostDTO;
import com.txy.blog.payload.PostPagination;
import com.txy.blog.security.JwtTokenProvider;
import com.txy.blog.service.CategoryService;
import com.txy.blog.service.PostService;
import com.txy.blog.service.RoleService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
//@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @MockBean
    private PostService postService;

    @MockBean
    private ModelMapper modelMapper;

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
                .description("test test test")
                .categoryId(1L)
                .comments(new HashSet<>())
                .build();
    }

    @Test
    void createPost() throws Exception {
        when(postService.createPost(any(PostDTO.class))).thenReturn(mock(Post.class));
        when(modelMapper.map(any(Post.class), eq(PostDTO.class))).thenReturn(postDTO);
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postDTO.getContent())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(postDTO.getDescription())))
                .andExpect(jsonPath("$.categoryId", CoreMatchers.is(1)))
                .andDo(print());
    }

    @Test
    void getAllPosts() throws Exception {
        PostPagination postPagination = PostPagination.builder()
                .pageNo(1)
                .pageSize(1)
                .content(Arrays.asList(postDTO))
                .build();

        when(postService.getAllPosts(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(postPagination);
        mockMvc.perform(get("/api/v1/posts")
                        .param("pageNo", "1")
                        .param("pageSize", "1")
                        .param("sortBy", "id")
                        .param("isAsc", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(postPagination.getContent().size())))
                .andDo(print());
    }

    @Test
    void getPostById() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(mock(Post.class));
        when(modelMapper.map(any(Post.class), eq(PostDTO.class))).thenReturn(postDTO);

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postDTO.getContent())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(postDTO.getDescription())))
                .andExpect(jsonPath("$.categoryId", CoreMatchers.is(1)))
                .andDo(print());
    }

    @Test
    void updatePostById() throws Exception {
        Long postId = 1L;
        when(postService.updatePostById(postId, postDTO)).thenReturn(post);
        when(modelMapper.map(eq(post), eq(PostDTO.class))).thenReturn(postDTO);

        mockMvc.perform(put("/api/v1/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postDTO.getContent())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(postDTO.getDescription())))
                .andExpect(jsonPath("$.categoryId", CoreMatchers.is(1)))
                .andDo(print());
    }

    @Test
    void deletePostById() throws Exception {
        doNothing().when(postService).deletePostById(anyLong());

        mockMvc.perform(delete("/api/v1/posts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsByCategoryId() throws Exception {
        Long categoryId = 1L;
        List<Post> posts = Arrays.asList(post);
        when(postService.getPostsByCategoryId(categoryId)).thenReturn(posts);
        when(modelMapper.map(eq(post), eq(PostDTO.class))).thenReturn(postDTO);

        mockMvc.perform(get("/api/v1/posts/category/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(posts.size())))
                .andDo(print());
    }
}