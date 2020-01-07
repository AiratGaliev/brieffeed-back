package com.brieffeed.back.services;

import com.brieffeed.back.domain.*;
import com.brieffeed.back.exceptions.IdException;
import com.brieffeed.back.exceptions.NotFoundException;
import com.brieffeed.back.payload.PostRequest;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private BlogRepository blogRepository;

    private String getUserRole(String username) {
        return userRepository.findByUserName(username).getRole();
    }

    public Iterable<Post> findAll() {
        return postRepository.findAllByStatus(Status.PUBLISH.getStatus(), Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    public Iterable<Post> findAll(String username) {
        if (getUserRole(username).equals(Role.ADMIN.getRole()))
            return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        else {
            List<Post> posts = (List<Post>) postRepository.findAllByStatus(Status.PUBLISH.getStatus(), Sort.by(Sort.Direction.DESC, "createdDate"));
            if (getUserRole(username).equals(Role.AUTHOR.getRole())) {
                posts.addAll((Collection<? extends Post>) postRepository.findAllByAuthorAndStatus(username, Status.DRAFT.getStatus()));
            }
            return posts;
        }
    }

    public Iterable<Post> findAllByAuthor(String username) {
        return postRepository.findAllByAuthor(username);
    }

    public Post findById(String id) {
        try {
            Post post = postRepository.findPostById(Long.parseLong(id));
            if (post.getStatus().equals(Status.DRAFT.getStatus())) {
                throw new NotFoundException("Post not found in your account");
            }
            return post;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new IdException("Post ID: '" + id + "' does not exists");
        }
    }

    public Post findById(String username, String id) {
        try {
            Post post = postRepository.findPostById(Long.parseLong(id));
            if (!(post.getAuthor().equals(username) || getUserRole(username).equals(Role.ADMIN.getRole()))) {
                throw new NotFoundException("Post not found in your account");
            }
            return post;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new IdException("Post ID: '" + id + "' does not exists");
        }
    }

    public Post create(PostRequest postRequest, String username) {
        User user = userRepository.findByUserName(username);
        Blog blog = blogRepository.findBlogById(postRequest.getBlogId());
        if (user.getRole().equals(Role.AUTHOR.getRole())) {
            Post post = new Post(postRequest.getTitle(), postRequest.getContent(), blog, user, user.getUsername(), postRequest.getStatus());
            return postRepository.save(post);
        } else
            throw new NotFoundException("You do not have permission to create post.");
    }

    public Post update(PostRequest postRequest, String id, String username) {
        Post originalPost = findById(username, id);
        if (originalPost.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole())) {
            originalPost.setTitle(postRequest.getTitle());
            originalPost.setContent(postRequest.getContent());
            originalPost.setStatus(postRequest.getStatus());
            originalPost.setBlog(blogRepository.findBlogById(postRequest.getBlogId()));
            return postRepository.save(originalPost);
        } else
            throw new NotFoundException("You do not have permission to update the post.");
    }

    public void delete(String username, String id) {
        Post post = findById(username, id);
        if (post.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole()) || getUserRole(username).equals(Role.ADMIN.getRole())) {
            postRepository.delete(post);
        } else
            throw new NotFoundException("You do not have permission to delete the post.");
    }
}
