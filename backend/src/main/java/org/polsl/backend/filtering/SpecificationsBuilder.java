package org.polsl.backend.filtering;

import org.polsl.backend.entity.Software;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationsBuilder {
  private final List<SearchCriteria> params;

  public SpecificationsBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public SpecificationsBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Software> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification> specs = params.stream()
            .map(SearchSpecification::new)
            .collect(Collectors.toList());

    Specification result = specs.get(0);

    for (int i = 1; i < params.size(); i++) {
      result = Specification.where(result).and(specs.get(i));
    }
    return result;
  }
}
