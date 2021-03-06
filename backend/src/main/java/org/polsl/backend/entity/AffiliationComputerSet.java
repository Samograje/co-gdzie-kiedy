package org.polsl.backend.entity;

import org.polsl.backend.key.AffiliationComputerSetKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca bazodanową encję wpisu historii dotyczącego połączenia zestawu komputerowego z przynależnością.
 */
@Entity
@Table(name = "affiliations_computer_sets")
public class AffiliationComputerSet {

  @EmbeddedId
  private AffiliationComputerSetKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("affiliation_id")
  @JoinColumn(name = "affiliation_id", nullable = false, insertable = false, updatable = false)
  private Affiliation affiliation;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("computer_set_id")
  @JoinColumn(name = "computer_set_id", nullable = false, insertable = false, updatable = false)
  private ComputerSet computerSet;

  @Column(name = "valid_from", nullable = false, insertable = false, updatable = false)
  @MapsId("valid_from")
  private LocalDateTime validFrom;

  private LocalDateTime validTo;

  public AffiliationComputerSet() {
  }

  public AffiliationComputerSet(Affiliation affiliation, ComputerSet computerSet) {
    this.affiliation = affiliation;
    this.computerSet = computerSet;
    this.validFrom = LocalDateTime.now();
    this.id = new AffiliationComputerSetKey(affiliation.getId(), computerSet.getId(), this.validFrom);
  }

  public AffiliationComputerSetKey getId() {
    return id;
  }

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
