package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Image extends AbstractEntity {

  @Column(name = "alt")
  private String description;
  private String name;
  private byte[] imgBytes;

  public Image() {
  }

  public Image(String description, String name, byte[] imgBytes) {
    this.description = description;
    this.name = name;
    this.imgBytes = imgBytes;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public byte[] getImgBytes() {
    return imgBytes;
  }

  public void setImgBytes(byte[] imgBytes) {
    this.imgBytes = imgBytes;
  }
}
