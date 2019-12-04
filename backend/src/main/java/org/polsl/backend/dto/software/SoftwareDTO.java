package org.polsl.backend.dto.software;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public class SoftwareDTO {
  @NotEmpty
  private String name;
  @NotNull
  private Long availableKeys;
  @NotEmpty
  private String key;

  private Set<Long> computerSetIds;
  private LocalDateTime activeFrom;
  private LocalDateTime activeTo;


  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public Set<Long> getComputerSetIds() { return computerSetIds; }

  public void setComputerSetIds(Set<Long> computerSetIds) { this.computerSetIds = computerSetIds; }

  public LocalDateTime getActiveTo() { return activeTo; }

  public void setActiveTo(LocalDateTime activeTo) { this.activeTo = activeTo; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public LocalDateTime getActiveFrom() { return activeFrom; }

  public void setActiveFrom(LocalDateTime activeFrom) { this.activeFrom = activeFrom; }
}
