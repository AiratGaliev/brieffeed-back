package com.brieffeed.back.services;

import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Status;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.PostIdException;
import com.brieffeed.back.exceptions.PostNotFoundException;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.PostRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Post> findAll(String username) {
        if (userRepository.findByUserName(username).getRole().equals(Role.ADMIN.getRole()))
            return postRepository.findAll();
        else
            return null;
    }

    public Iterable<Post> findAllByAuthor(String username) {
        return postRepository.findAllByAuthor(username);
    }

    public Iterable<Post> findAllByAuthorAndStatus(String username) {
        List<Post> posts = (List<Post>) postRepository.findAllByStatus(Status.PUBLISH.getStatus());
        if (userRepository.findByUserName(username).getRole().equals(Role.AUTHOR.getRole())) {
            posts.addAll((Collection<? extends Post>) postRepository.findAllByAuthorAndStatus(username, Status.DRAFT.getStatus()));
        }
        return posts;
    }

    public Post findById(String username, String postId) {
        Post post = postRepository.findPostById(Long.parseLong(postId));
        if (post == null) {
            throw new PostIdException("Post ID: '" + postId + "' does not exists");
        }
        if (!post.getAuthor().equals(username) && post.getStatus().equals(Status.DRAFT.getStatus())) {
            throw new PostNotFoundException("Post not found in your account");
        }
        return post;
    }


    public Post create(Post newPost, String username) {
        User user = userRepository.findByUserName(username);
        if (user.getRole().equals(Role.AUTHOR.getRole())) {
            newPost.setUser(user);
            newPost.setAuthor(user.getUsername());
            return postRepository.save(newPost);
        } else
            throw new PostNotFoundException("You cannot create posts");
    }

    public Post update(Post updatedPost, String postId, String username) {
        Post originalPost = postRepository.findById(Long.parseLong(postId)).get();
        if (originalPost.getAuthor().equals(username)) {
            Post post1 = originalPost;
            post1.setTitle(updatedPost.getTitle());
            post1.setContent(updatedPost.getContent());
            post1.setStatus(updatedPost.getStatus());
            return postRepository.save(post1);
        } else
            throw new PostIdException("You cannot this update post");
    }

    public void delete(String username, String postId) {
        Post post = findById(username, postId);
        if (post.getAuthor().equals(username) || userRepository.findByUserName(username).getRole().equals(Role.ADMIN.getRole())) {
            postRepository.delete(post);
        } else
            throw new PostIdException("You cannot delete this post");
    }
}
