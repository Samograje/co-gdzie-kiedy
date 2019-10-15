package org.polsl.backend.entity;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="hardware")
    private Hardware hardware;

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

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
}
