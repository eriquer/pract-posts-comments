package es.codeurjc.posts.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "post")
public class Post {

    @Id
    private Long id;

    @Indexed
    private String author;

    @TextIndexed
    private String title;

    @TextIndexed
    private String text;

    private List<Comment> comments;
}