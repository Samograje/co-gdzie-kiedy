package org.polsl.backend.dto;

import java.util.List;

public class PaginatedResult<T> {
  private List<T> items;
  private Long totalElements;

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }
}
