package org.polsl.backend.dto.software;


import javax.validation.constraints.NotEmpty;

public class SoftwareInputDTO {
  @NotEmpty
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
