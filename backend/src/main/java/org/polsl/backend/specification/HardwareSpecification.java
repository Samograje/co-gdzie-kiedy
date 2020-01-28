package org.polsl.backend.specification;

import org.polsl.backend.entity.Hardware;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.service.filtering.SearchCriteria;
import org.polsl.backend.service.filtering.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class HardwareSpecification implements SearchSpecification<Hardware> {
  private SearchCriteria criteria;

  @Override
  public void setCriteria(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public SearchCriteria getCriteria() {
    return criteria;
  }

  @Override
  public Expression getPredicatePath(Root<Hardware> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    switch (criteria.getKey()) {
      case "name":
        return root.get("name");
      case "type":
        return root.get("hardwareDictionary").get("value");
      case "inventoryNumber":
        return root.get("inventoryNumber");
      // TODO: wyszukiwanie po pozostałych polach z klasy dto
      default:
        throw new BadRequestException("Parametr search nie został prawidłowo zakodowany");
    }
  }
}
