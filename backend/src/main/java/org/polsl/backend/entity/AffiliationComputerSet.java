package org.polsl.backend.entity;

import org.polsl.backend.key.AffiliationComputerSetKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "affiliations_computer_sets")
public class AffiliationComputerSet {

  @EmbeddedId
  private AffiliationComputerSetKey id;

  @ManyToOne
  @MapsId("affiliation_id")
  @JoinColumn(name = "affiliation_id", insertable = false, updatable = false)
  private Affiliation affiliation;

  @ManyToOne
  @MapsId("computer_set_id")
  @JoinColumn(name = "computer_set_id", insertable = false, updatable = false)
  private ComputerSet computerSet;

  @Column(name = "valid_from", insertable = false, updatable = false)
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public void setId(AffiliationComputerSetKey id) {
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
