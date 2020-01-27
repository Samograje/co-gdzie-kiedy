package org.polsl.backend.specification;

import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.service.filtering.SearchCriteria;
import org.polsl.backend.service.filtering.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class SoftwareSpecification implements SearchSpecification<Software> {
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
  public Expression getPredicatePath(Root<Software> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    switch (criteria.getKey()) {
      case "name":
        return root.get("name");
      case "inventoryNumber":
        return root.get("inventoryNumber");
      case "key":
        return root.get("key");
      // TODO: wyszukiwanie po pozostałych polach z klasy dto
      default:
        throw new BadRequestException("Parametr search nie został prawidłowo zakodowany");
    }
  }
}
