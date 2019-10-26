package org.polsl.backend.entity;

import org.polsl.backend.key.ComputerSetHardwareKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "computer_sets_hardware")
public class ComputerSetHardware {
  @EmbeddedId
  private ComputerSetHardwareKey id;

  @ManyToOne
  @MapsId("computer_set_id")
  @JoinColumn(name = "computer_set_id", insertable = false, updatable = false)
  private ComputerSet computerSet;

  @ManyToOne
  @MapsId("hardware_id")
  @JoinColumn(name = "hardware_id", insertable = false, updatable = false)
  private Hardware hardware;

  @Column(name = "valid_from", insertable = false, updatable = false)
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public void setId(ComputerSetHardwareKey id) {
    this.id = id;
  }

  public ComputerSet getComputerSet() {
    return computerSet;
  }

  public void setComputerSet(ComputerSet computerSet) {
    this.computerSet = computerSet;
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
