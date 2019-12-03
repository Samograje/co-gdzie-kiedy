package org.polsl.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Klasa reprezentująca bazodanową encję oprogramowania.
 */
@Entity
@Table(name = "software")
public class Software {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private String inventoryNumber;
  private String key;
  private Long availableKeys;
  private LocalDateTime activeFrom;
  private LocalDateTime  validUntil;
  private LocalDateTime validTo;

  @OneToMany(mappedBy = "software")
  private Set<ComputerSetSoftware> computerSetSoftwareSet;

  public Software() {
  }

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

  public Set<ComputerSetSoftware> getComputerSetSoftwareSet() {
    return computerSetSoftwareSet;
  }

  public void setComputerSetSoftwareSet(Set<ComputerSetSoftware> computerSetSoftwareSet) {
    this.computerSetSoftwareSet = computerSetSoftwareSet;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
