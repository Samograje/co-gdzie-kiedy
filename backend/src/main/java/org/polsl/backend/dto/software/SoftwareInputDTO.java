package org.polsl.backend.dto.software;

import org.polsl.backend.entity.ComputerSetSoftware;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class SoftwareInputDTO {
  @NotEmpty
  private String name;

  private Set<ComputerSetSoftware> computerSetSoftwareSet;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
