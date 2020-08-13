package com.brieffeed.back.services;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.IdException;
import com.brieffeed.back.exceptions.NotFoundException;
import com.brieffeed.back.payload.BlogRequest;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

  private final BlogRepository blogRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public BlogService(BlogRepository blogRepository,
      UserRepository userRepository, CategoryRepository categoryRepository) {
    this.blogRepository = blogRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  private String getUserRole(String username) {
    return userRepository.findByUserName(username).getRole();
  }

  public Iterable<Blog> findAll() {
    return blogRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
  }

  public Iterable<Blog> findAllByAuthor(String username) {
    return blogRepository.findAllByAuthor(username);
  }

  public Blog findById(String id) {
    return blogRepository.findBlogById(Long.parseLong(id));
  }

  public Blog findById(String username, String id) {
    try {
      Blog blog = findById(id);
      if (!(blog.getAuthor().equals(username) || getUserRole(username)
          .equals(Role.ADMIN.getRole()))) {
        throw new NotFoundException("Blog not found in your account");
      }
      return blog;
    } catch (NoSuchElementException | NullPointerException e) {
      throw new IdException("Blog ID: '" + id + "' does not exists");
    }
  }

  public Blog create(BlogRequest blogRequest, String username) {
    User user = userRepository.findByUserName(username);
    Category category = categoryRepository.findCategoryById(blogRequest.getCategoryId());
    if (user.getRole().equals(Role.AUTHOR.getRole())) {
      Blog newBlog = new Blog(category, blogRequest.getName(), user, blogRequest.getDescription(),
          user.getUsername());
      return blogRepository.save(newBlog);
    } else {
      throw new NotFoundException("You do not have permission to create blog.");
    }
  }

  public Blog update(BlogRequest blogRequest, String id, String username) {
    Blog originalBlog = findById(username, id);
    if (originalBlog.getAuthor().equals(username) && getUserRole(username)
        .equals(Role.AUTHOR.getRole())) {
      originalBlog.setName(blogRequest.getName());
      originalBlog.setDescription(blogRequest.getDescription());
      originalBlog.setCategory(categoryRepository.findCategoryById(blogRequest.getCategoryId()));
      return blogRepository.save(originalBlog);
    } else {
      throw new NotFoundException("You do not have permission to update the blog.");
    }
  }

  public void delete(String username, String id) {
    Blog blog = findById(username, id);
    if (blog.getAuthor().equals(username) && getUserRole(username).equals(Role.AUTHOR.getRole())
        || getUserRole(username).equals(Role.ADMIN.getRole())) {
      blogRepository.delete(blog);
    } else {
      throw new NotFoundException("You do not have permission to delete the blog.");
    }
  }
}
