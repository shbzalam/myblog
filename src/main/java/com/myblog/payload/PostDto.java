package com.myblog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Post title should at least 3 characters")
    private String title;

    @NotEmpty
    @Size(min = 4, message = "Post description should at least 4 characters")
    private String description;

    @NotEmpty
    @Size(min = 5, message = "Post content should at least 5 characters")
    private String content;
}
