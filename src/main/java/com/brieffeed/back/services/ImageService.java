package com.brieffeed.back.services;

import com.brieffeed.back.domain.Image;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.IdException;
import com.brieffeed.back.exceptions.NotFoundException;
import com.brieffeed.back.payload.ImageRequest;
import com.brieffeed.back.repositories.ImageRepository;
import com.brieffeed.back.repositories.UserRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageService {

  private final ImageRepository imageRepository;
  private final UserRepository userRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository,
      UserRepository userRepository) {
    this.imageRepository = imageRepository;
    this.userRepository = userRepository;
  }

  public Image findById(String id) {
    try {
      Image image = imageRepository.findImageById(Long.parseLong(id));
      return new Image(image.getDescription(), image.getName(),
          decompressImage(image.getImgBytes()));
    } catch (NoSuchElementException | NullPointerException e) {
      throw new IdException("Image ID: '" + id + "' does not exists");
    }
  }

  public Image upload(ImageRequest imageRequest, String username) throws IOException {
    User user = userRepository.findByUserName(username);
    if (user.getRole().equals(Role.AUTHOR.getRole())) {
      Image image = new Image(imageRequest.getDescription(),
          imageRequest.getMultipartFile().getName(),
          compressImage(imageRequest.getMultipartFile().getBytes()));
      return imageRepository.save(image);
    }
    throw new NotFoundException("You do not have permission to create post.");
  }

  private static byte[] compressImage(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      byteArrayOutputStream.write(buffer, 0, count);
    }
    try {
      byteArrayOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("Compressed Image Byte Size - {}", byteArrayOutputStream.toByteArray().length);
    return byteArrayOutputStream.toByteArray();
  }

  private static byte[] decompressImage(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        byteArrayOutputStream.write(buffer, 0, count);
      }
      byteArrayOutputStream.close();
    } catch (DataFormatException | IOException e) {

    }
    return byteArrayOutputStream.toByteArray();
  }
}
