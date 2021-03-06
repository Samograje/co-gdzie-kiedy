package org.polsl.backend.entity;

import org.polsl.backend.key.AffiliationHardwareKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca bazodanową encję wpisu historii dotyczącego połączenia hardware'u z przynależnością.
 */
@Entity
@Table(name = "affiliations_hardware")
public class AffiliationHardware {

  @EmbeddedId
  private AffiliationHardwareKey id;

  @ManyToOne
  @MapsId("affiliation_id")
  @JoinColumn(name = "affiliation_id", insertable = false, updatable = false)
  private Affiliation affiliation;

  @ManyToOne
  @MapsId("hardware_id")
  @JoinColumn(name = "hardware_id", insertable = false, updatable = false)
  private Hardware hardware;

  @Column(name = "valid_from", insertable = false, updatable = false)
  @MapsId("valid_from")
  private LocalDateTime validFrom;

  private LocalDateTime validTo;

  public AffiliationHardware() {
  }

  public AffiliationHardware(Affiliation affiliation, Hardware hardware) {
    this.affiliation = affiliation;
    this.hardware = hardware;
    this.validFrom = LocalDateTime.now();
    this.id = new AffiliationHardwareKey(affiliation.getId(), hardware.getId(), this.validFrom);
  }

  public AffiliationHardwareKey getId() {
    return id;
  }

  public void setId(AffiliationHardwareKey id) {
    this.id = id;
  }

  public Affiliation getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(Affiliation affiliation) {
    this.affiliation = affiliation;
  }

  public Hardware getHardware() {
    return hardware;
  }

  public void setHardware(Hardware hardware) {
    this.hardware = hardware;
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
