package org.polsl.backend.dto.software;

import com.sun.istack.NotNull;

public class SoftwareInputDTO {
    @NotNull
    private String name;

    public String getName() { return name; }
    public void setName(String name){this.name = name; }
}
