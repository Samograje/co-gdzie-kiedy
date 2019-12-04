package org.polsl.backend.dto.software;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

public class SoftwareDTO {
  @NotEmpty
  private String name;

  private Set<Long> computerSetIds;

  private LocalDateTime activeTo;

  private Long availableKeys;

  private String key;

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
}
