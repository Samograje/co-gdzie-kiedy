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

    @ManyToMany
    @JoinTable(name = "affiliations_hardwares",
            joinColumns = @JoinColumn(name = "affiliation_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    private Set<Hardware> hardwares;

    @ManyToMany
    @JoinTable(name = "affiliations_computer_sets",
            joinColumns = @JoinColumn(name = "affiliation_id"),
            inverseJoinColumns = @JoinColumn(name = "computer_set_id"))
    private Set<ComputerSet> computerSets;

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

    public Set<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(Set<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public Set<ComputerSet> getComputerSets() {
        return computerSets;
    }

    public void setComputerSets(Set<ComputerSet> computerSets) {
        this.computerSets = computerSets;
    }
}
