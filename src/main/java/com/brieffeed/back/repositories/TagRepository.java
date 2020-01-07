package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
}
