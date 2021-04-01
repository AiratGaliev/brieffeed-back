package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

  Image findImageById(Long aLong);
}
