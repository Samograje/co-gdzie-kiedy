package org.polsl.backend.dto.software;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class SoftwareInputDTO {
  @NotEmpty
  private String name;

  private Set<Long> computerSetIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Long> getComputerSetIds() {
    return computerSetIds;
  }

  public void setComputerSetIds(Set<Long> computerSetIds) {
    this.computerSetIds = computerSetIds;
  }
}
