package org.polsl.backend.dto.affiliation;

public class AffiliationOutputDTO {
  private Long id;
  private String name;
  private Boolean deleted;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }
}
