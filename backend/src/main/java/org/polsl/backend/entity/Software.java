package org.polsl.backend.entity;

import org.polsl.backend.key.ComputerSetSoftwareKey;

import javax.persistence.*;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "software")
public class Software {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "software")
  private Set<ComputerSetSoftware> computerSetSoftwares;

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

  public Set<ComputerSetSoftware> getSoftwareHardwareSet() {
    return computerSetSoftwares;
  }

  public void setSoftwareHardwareSet(Set<ComputerSetSoftware> computerSetSoftware) {
    this.computerSetSoftwares = computerSetSoftware;
  }
}
