package com.brieffeed.back.services;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.exceptions.IdException;
import com.brieffeed.back.exceptions.NotFoundException;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  @Autowired
  public CategoryService(
      CategoryRepository categoryRepository, UserRepository userRepository) {
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }

  private String getUserRole(String username) {
    return userRepository.findByUserName(username).getRole();
  }

  public Iterable<Category> findAll() {
    return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
  }

  public Category create(Category newCategory, String username) {
    if (getUserRole(username).equals(Role.ADMIN.getRole())) {
      return categoryRepository.save(newCategory);
    } else {
      throw new NotFoundException("You do not have permission to create category.");
    }
  }

  public Category findById(String id) {
    return categoryRepository.findCategoryById(Long.parseLong(id));
  }

  public Category findById(String username, String id) {
    try {
      Category category = findById(id);
      if (!getUserRole(username).equals(Role.ADMIN.getRole())) {
        throw new NotFoundException("Category not found");
      }
      return category;
    } catch (NoSuchElementException | NullPointerException e) {
      throw new IdException("Category ID: '" + id + "' does not exists");
    }
  }

  public Category update(Category category, String id, String username) {
    Category originalCategory = findById(username, id);
    if (getUserRole(username).equals(Role.ADMIN.getRole())) {
      originalCategory.setName(category.getName());
      return categoryRepository.save(originalCategory);
    } else {
      throw new NotFoundException("You do not have permission to update the category.");
    }
  }

  public void delete(String username, String id) {
    Category category = findById(username, id);
    if (getUserRole(username).equals(Role.ADMIN.getRole())) {
      categoryRepository.delete(category);
    } else {
      throw new NotFoundException("You do not have permission to delete the category.");
    }
  }
}
