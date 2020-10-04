package es.codeurjc.posts.controller;

import es.codeurjc.posts.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PostDTO> createPost(@RequestBody Mono<PostDTO> postDto) {

        return postDto.map(this::toPost)
                      .flatMap(postService::createPost)
                      .map(this::toPostDTO);
    }

    @GetMapping("")
    public Flux<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Mono<Post> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @DeleteMapping("/{id}")
    public Mono<PostDTO> deletePost(@PathVariable Long id) {
        return postService.deletePost(id)
                          .map(this::toPostDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PostDTO> updatePost(@PathVariable Long id, @RequestBody Mono<PostDTO> postDto) {

        return postDto.map(this::toPost)
                      .flatMap(p -> {
                          return postService.updatePost(id, p);
                      })
                      .map(this::toPostDTO);
    }

    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PostDTO> createComment(@PathVariable Long id, @RequestBody Mono<CommentDTO> commentDto) {

        return commentDto.map(this::toComment)
                      .flatMap(c -> {
                          return postService.updateComment(id, c);
                      })
                      .map(this::toPostDTO);
    }

    @DeleteMapping("/{id}/comment/{idComment}")
    public Mono<PostDTO> deletePost(@PathVariable Long id, @PathVariable Long idComment) {
        return postService.deleteComment(id, idComment)
                          .map(this::toPostDTO);
    }


    private Post toPost(PostDTO postDTO) {
        return new Post(postDTO.getId(), postDTO.getAuthor(), postDTO.getTitle(), postDTO.getText(), postDTO.getComments());
    }

    private PostDTO toPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getAuthor(), post.getTitle(), post.getText(), post.getComments());
    }

    private Comment toComment(CommentDTO commentDTO) {
        return new Comment(commentDTO.getId(), commentDTO.getAuthor(), commentDTO.getContent(), commentDTO.getDate());
    }

    private CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getAuthor(), comment.getContent(), comment.getDate());
    }

}


