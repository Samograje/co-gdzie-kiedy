package org.polsl.backend.dto.affiliation;

import javax.validation.constraints.NotNull;

public class AffiliationDTO {
  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  private String location;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
