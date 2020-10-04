package es.codeurjc.posts.service;

import es.codeurjc.posts.controller.Comment;
import es.codeurjc.posts.controller.Post;
import es.codeurjc.posts.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    public Mono<Post> createPost(Post post) {
        return postRepository.save(post);
    }

    public Mono<Post> getPost(Long id) {

        return postRepository.findById(id)
                             .switchIfEmpty(
                                     Mono.error(new ResponseStatusException(
                                             HttpStatus.NOT_FOUND, "Post with id "+id+" not found")));
    }

    public Flux<Post> getAllPosts() {

        return postRepository.findAll()
                .switchIfEmpty( Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No posts found!")));
    }

    public Mono<Post> deletePost(Long id) {

        Mono<Post> postToDelete = postRepository.findById(id);
        return postToDelete.switchIfEmpty(Mono.error(new ResponseStatusException(
                                 HttpStatus.NOT_FOUND, "No post found. The post can't be deleted")))
                           .flatMap(post -> postRepository.deleteById(id)
                                                          .then(Mono.just(post)));
    }

    public Mono<Post> updatePost(Long id, Post newValuesPost) {

        Mono<Post> postToUpdate = postRepository.findById(id);
        return postToUpdate
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No post found. The update can't be done")))
                .flatMap(post -> {
                    if (newValuesPost.getAuthor() != null) {
                        post.setAuthor(newValuesPost.getAuthor());
                    }

                    if (newValuesPost.getTitle() != null) {
                        post.setTitle(newValuesPost.getTitle());
                    }

                    if (newValuesPost.getText() != null) {
                        post.setText(newValuesPost.getText());
                    }

                    return postRepository.save(post);
                });
    }

    public Mono<Post> updateComment(Long id, Comment newComment) {

        Mono<Post> postToUpdate = postRepository.findById(id);
        return postToUpdate
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No post found. The new comment can't be addes")))
                .flatMap(post -> {
                    post.getComments().add(newComment);
                    return postRepository.save(post);
                });

    }

    public Mono<Post> deleteComment(Long id, Long idComment) {

        Mono<Post> postToUpdate = postRepository.findById(id);

        return postToUpdate
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No post found. The comment can't be deleted")))
                .flatMap(post -> {
                    removeComment(idComment, post.getComments());
                    return postRepository.save(post);
        });
    }

    private List<Comment> removeComment(Long idComment, List<Comment> comments) {

        Optional<Comment> comment = comments.stream()
                                            .filter(c -> c.getId().longValue() == idComment.longValue())
                                            .findFirst();

        comment.ifPresent(comments::remove);

        return comments;
    }
}