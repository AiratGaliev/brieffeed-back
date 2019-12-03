package com.brieffeed.back.services;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Status;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.PostIdException;
import com.brieffeed.back.exceptions.PostNotFoundException;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private String getUserRole(String username) {
        return userRepository.findByUserName(username).getRole();
    }

    public Iterable<Post> findAll() {
        return postRepository.findAllByStatus(Status.PUBLISH.getStatus());
    }

    public Iterable<Post> findAll(String username) {
        if (getUserRole(username).equals(Role.ADMIN.getRole()))
            return postRepository.findAll();
        else {
            List<Post> posts = (List<Post>) postRepository.findAllByStatus(Status.PUBLISH.getStatus());
            if (getUserRole(username).equals(Role.AUTHOR.getRole())) {
                posts.addAll((Collection<? extends Post>) postRepository.findAllByAuthorAndStatus(username, Status.DRAFT.getStatus()));
            }
            return posts;
        }
    }

    public Iterable<Post> findAllByAuthor(String username) {
        return postRepository.findAllByAuthor(username);
    }

    public Post findById(String postId) {
        try {
            Post post = postRepository.findPostById(Long.parseLong(postId));
            if (post.getStatus().equals(Status.DRAFT.getStatus())) {
                throw new PostNotFoundException("Post not found in your account");
            }
            return post;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new PostIdException("Post ID: '" + postId + "' does not exists");
        }
    }

    public Post findById(String username, String postId) {
        try {
            Post post = postRepository.findPostById(Long.parseLong(postId));
            if (!(post.getAuthor().equals(username) || getUserRole(username).equals(Role.ADMIN.getRole()))) {
                throw new PostNotFoundException("Post not found in your account");
            }
            return post;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new PostIdException("Post ID: '" + postId + "' does not exists");
        }
    }

    public Post create(Post newPost, String username) {
        User user = userRepository.findByUserName(username);
        if (user.getRole().equals(Role.AUTHOR.getRole())) {
            newPost.setUser(user);
            newPost.setAuthor(user.getUsername());
            return postRepository.save(newPost);
        } else
            throw new PostNotFoundException("You do not have permission to create posts.");
    }

    public Post update(Post updatedPost, String postId, String username) {
        Post originalPost = findById(username, postId);
        if (originalPost.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole())) {
            originalPost.setTitle(updatedPost.getTitle());
            originalPost.setContent(updatedPost.getContent());
            originalPost.setStatus(updatedPost.getStatus());
            return postRepository.save(originalPost);
        } else
            throw new PostIdException("You do not have permission to update the post.");
    }

    public void delete(String username, String postId) {
        Post post = findById(username, postId);
        if (post.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole()) || getUserRole(username).equals(Role.ADMIN.getRole())) {
            postRepository.delete(post);
        } else
            throw new PostIdException("You do not have permission to delete the post.");
    }
}
