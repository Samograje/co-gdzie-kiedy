package org.polsl.backend.dto.hardwaredictionary;

public class HardwareDictionaryOutputDTO {
  private Long id;
  private String value;

  public HardwareDictionaryOutputDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
