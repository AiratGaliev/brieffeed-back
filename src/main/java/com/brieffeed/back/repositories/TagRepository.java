package com.brieffeed.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
