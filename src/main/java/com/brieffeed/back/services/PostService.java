package com.brieffeed.back.services;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post create(Post newPost, String username) {
		User user = userRepository.findByUserName(username);
		newPost.setUser(user);
		newPost.setAuthor(user.getUsername());
        return postRepository.save(newPost);
    }

    public Post update(Post updatedPost, String postId) {
        Post originalPost = postRepository.findById(Long.valueOf(postId)).get();
        Post post1 = originalPost;
        post1.setTitle(updatedPost.getTitle());
        post1.setContent(updatedPost.getContent());
        post1.setStatus(updatedPost.getStatus());
        return postRepository.save(post1);
    }
}
