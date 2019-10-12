package org.polsl.inventory.entity;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "hardware_dictionary")
public class HardwareDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    private String value;

    @OneToOne(mappedBy = "hardwareDictionary")
    private Hardware hardware;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
}
