package com.txy.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty()
    private String username;

    @NotEmpty()
    @Email
    private String email;

    @NotEmpty()
    @Size(min = 1, message = "Comment should have at least 1 character.")
    private String content;
}
