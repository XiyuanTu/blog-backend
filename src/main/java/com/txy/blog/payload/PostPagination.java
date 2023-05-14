package com.txy.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPagination {
    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalPosts;
    private int totalPages;
    private boolean isLastPage;
}
