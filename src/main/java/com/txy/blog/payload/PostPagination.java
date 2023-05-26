package com.txy.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPagination {
    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalPosts;
    private int totalPages;
    private boolean isLastPage;
}
