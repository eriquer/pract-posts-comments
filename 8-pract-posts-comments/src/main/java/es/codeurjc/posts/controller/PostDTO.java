package es.codeurjc.posts.controller;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
public class PostDTO {

    @Nullable
    private Long id;

    @Nullable
    private String author;

    @Nullable
    private String title;

    @Nullable
    private String text;

    @Nullable
    private List<Comment> comments;
}
