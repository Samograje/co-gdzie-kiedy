package org.polsl.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "affiliations_computer_sets")
public class AffiliationComputerSet {

  @Id
  private Long id;

  @ManyToOne
  @JoinColumn(name = "affiliation_id")
  private Affiliation affiliation;

  @ManyToOne
  @JoinColumn(name = "computer_set_id")
  private ComputerSet computerSet;

  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Affiliation getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(Affiliation affiliation) {
    this.affiliation = affiliation;
  }

  public ComputerSet getComputerSet() {
    return computerSet;
  }

  public void setComputerSet(ComputerSet computerSet) {
    this.computerSet = computerSet;
  }

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
