package org.polsl.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

  private LocalDateTime valid_to;

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

  public LocalDateTime getValid_to() {
    return valid_to;
  }

  public void setValid_to(LocalDateTime valid_to) {
    this.valid_to = valid_to;
  }
}
