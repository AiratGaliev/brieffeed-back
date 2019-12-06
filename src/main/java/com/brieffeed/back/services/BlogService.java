package com.brieffeed.back.services;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.PostIdException;
import com.brieffeed.back.exceptions.PostNotFoundException;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    private String getUserRole(String username) {
        return userRepository.findByUserName(username).getRole();
    }

    public Iterable<Blog> findAll() {
        return blogRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Blog create(Blog newBlog, String username) {
        User user = userRepository.findByUserName(username);
        if (user.getRole().equals(Role.AUTHOR.getRole())) {
            newBlog.setUser(user);
            newBlog.setAuthor(user.getUsername());
            return blogRepository.save(newBlog);
        } else
            throw new PostNotFoundException("You do not have permission to create blog.");
    }

	public Iterable<Blog> findAllByAuthor(String username) {
		return blogRepository.findAllByAuthor(username);
	}

    public Blog findById(String username, String blogId) {
        try {
            Blog blog = blogRepository.findBlogById(Long.parseLong(blogId));
            if (!(blog.getAuthor().equals(username) || getUserRole(username).equals(Role.ADMIN.getRole()))) {
                throw new PostNotFoundException("Blog not found in your account");
            }
            return blog;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new PostIdException("Blog ID: '" + blogId + "' does not exists");
        }
    }

    public Blog update(Blog updatedBlog, String blogId, String username) {
        Blog originalBlog = findById(username, blogId);
        if (originalBlog.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole())) {
            originalBlog.setName(updatedBlog.getName());
            originalBlog.setDescription(updatedBlog.getDescription());
            originalBlog.setCategory(updatedBlog.getCategory());
            return blogRepository.save(originalBlog);
        } else
            throw new PostIdException("You do not have permission to update the blog.");
    }

    public void delete(String username, String blogId) {
        Blog blog = findById(username, blogId);
        if (blog.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole()) || getUserRole(username).equals(Role.ADMIN.getRole())) {
            blogRepository.delete(blog);
        } else
            throw new PostIdException("You do not have permission to delete the blog.");
    }
}
