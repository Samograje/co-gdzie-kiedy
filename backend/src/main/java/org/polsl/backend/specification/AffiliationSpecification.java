package org.polsl.backend.specification;

import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.service.filtering.SearchCriteria;
import org.polsl.backend.service.filtering.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class AffiliationSpecification implements SearchSpecification<Affiliation> {
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
  public Expression getPredicatePath(Root<Affiliation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    switch (criteria.getKey()) {
      case "firstName":
        return root.get("firstName");
      case "lastName":
        return root.get("lastName");
      case "location":
        return root.get("location");
      // TODO: wyszukiwanie po pozostałych polach z klasy dto
      default:
        throw new BadRequestException("Parametr search nie został prawidłowo zakodowany");
    }
  }
}
