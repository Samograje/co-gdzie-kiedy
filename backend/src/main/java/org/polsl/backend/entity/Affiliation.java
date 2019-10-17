package org.polsl.backend.entity;

import javax.persistence.*;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "affiliations")
public class Affiliation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "affiliation")
    private Set<AffiliationHardware> affiliationHardwareSet;

    @OneToMany(mappedBy = "affiliation")
    private Set<AffiliationComputerSet> affiliationComputerSetSet;

    public Affiliation() {
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

    public Set<AffiliationHardware> getAffiliationHardwareSet() {
        return affiliationHardwareSet;
    }

    public void setAffiliationHardwareSet(Set<AffiliationHardware> affiliationHardwareSet) {
        this.affiliationHardwareSet = affiliationHardwareSet;
    }

    public Set<AffiliationComputerSet> getAffiliationComputerSetSet() {
        return affiliationComputerSetSet;
    }

    public void setAffiliationComputerSetSet(Set<AffiliationComputerSet> affiliationComputerSetSet) {
        this.affiliationComputerSetSet = affiliationComputerSetSet;
    }
}
