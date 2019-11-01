package org.polsl.backend.dto.computerset;

import org.polsl.backend.entity.Affiliation;

public class ComputerSetInputDTO {
  private String name;
  private Affiliation affiliation;
  /*private Set<Hardware> hardwareSet;
  private Set<Software> softwareSet;*/

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

  /*public Set<Hardware> getHardwareSet() {
    return hardwareSet;
  }

  public void setHardwareSet(Set<Hardware> hardwareSet) {
    this.hardwareSet = hardwareSet;
  }

  public Set<Software> getSoftwareSet() {
    return softwareSet;
  }

  public void setSoftwareSet(Set<Software> softwareSet) {
    this.softwareSet = softwareSet;
  }*/
}
