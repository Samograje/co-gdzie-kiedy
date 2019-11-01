package org.polsl.backend.dto.computerset;

import org.polsl.backend.entity.Affiliation;

import java.util.Set;

public class ComputerSetInputDTO {
  private String name;
  private Affiliation affiliation;
  //private Set<Long> hardwareIds;
  //private Set<Long> softwareIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Affiliation getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(Affiliation affiliation) {
    this.affiliation = affiliation;
  }
}
