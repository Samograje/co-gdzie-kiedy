package org.polsl.backend.dto.software;

import com.sun.istack.NotNull;

public class SoftwareDTO {
    //Ta klasa jest chyba niepotrzebna.
    @NotNull
    private String name;

    public String getName() { return name; }
    public void setName(String name){this.name = name; }
}
