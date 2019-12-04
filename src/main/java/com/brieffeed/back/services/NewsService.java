package com.brieffeed.back.services;

import com.brieffeed.back.domain.News;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Status;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.NewsIdException;
import com.brieffeed.back.exceptions.NewsNotFoundException;
import com.brieffeed.back.repositories.NewsRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    private String getUserRole(String username) {
        return userRepository.findByUserName(username).getRole();
    }

    public Iterable<News> findAll() {
        return newsRepository.findAllByStatus(Status.PUBLISH.getStatus());
    }

    public Iterable<News> findAll(String username) {
        if (getUserRole(username).equals(Role.ADMIN.getRole()))
            return newsRepository.findAll();
        else {
            List<News> news = (List<News>) newsRepository.findAllByStatus(Status.PUBLISH.getStatus());
            if (getUserRole(username).equals(Role.AUTHOR.getRole())) {
                news.addAll((Collection<? extends News>) newsRepository.findAllByAuthorAndStatus(username, Status.DRAFT.getStatus()));
            }
            return news;
        }
    }

    public Iterable<News> findAllByAuthor(String username) {
        return newsRepository.findAllByAuthor(username);
    }

    public News findById(String newsId) {
        try {
            News news = newsRepository.findNewsById(Long.parseLong(newsId));
            if (news.getStatus().equals(Status.DRAFT.getStatus())) {
                throw new NewsNotFoundException("News not found in your account");
            }
            return news;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new NewsIdException("News ID: '" + newsId + "' does not exists");
        }
    }

    public News findById(String username, String newsId) {
        try {
            News news = newsRepository.findNewsById(Long.parseLong(newsId));
            if (!(news.getAuthor().equals(username) && getUserRole(username).equals(Role.ADMIN.getRole()))) {
                throw new NewsNotFoundException("News not found in your account");
            }
            return news;
        } catch (NoSuchElementException | NullPointerException e) {
            throw new NewsIdException("News ID: '" + newsId + "' does not exists");
        }
    }

    public News create(News news, String username) {
        User user = userRepository.findByUserName(username);
        if (user.getRole().equals(Role.ADMIN.getRole())) {
            news.setUser(user);
            news.setAuthor(user.getUsername());
            return newsRepository.save(news);
        } else
            throw new NewsNotFoundException("You do not have permission to create news.");
    }

    public News update(News updatedNews, String newsId, String username) {
        News originalNews = findById(username, newsId);
        if (originalNews.getAuthor().equals(username) && getUserRole(username).equals(Role.ADMIN.getRole())) {
            originalNews.setTitle(updatedNews.getTitle());
            originalNews.setContent(updatedNews.getContent());
            originalNews.setStatus(updatedNews.getStatus());
            return newsRepository.save(originalNews);
        } else
            throw new NewsIdException("You do not have permission to update the news.");
    }

    public void delete(String username, String newsId) {
        News news = findById(username, newsId);
        if (news.getAuthor().equals(username) && getUserRole(username).equals(Role.ADMIN.getRole())) {
            newsRepository.delete(news);
        } else
            throw new NewsIdException("You do not have permission to delete the news.");
    }
}
