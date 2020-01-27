package org.polsl.backend.service.filtering;

import org.polsl.backend.exception.BadRequestException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SearchSpecification<T> extends Specification<T> {

  void setCriteria(SearchCriteria criteria);

  SearchCriteria getCriteria();

  Expression getPredicatePath(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

  @Override
  default Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) throws BadRequestException {

    SearchCriteria criteria = getCriteria();
    Expression path = getPredicatePath(root, query, builder);

    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return builder.greaterThanOrEqualTo(path, criteria.getValue().toString());
    }

    if (criteria.getOperation().equalsIgnoreCase("<")) {
      return builder.lessThanOrEqualTo(path, criteria.getValue().toString());
    }

    if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (path.getJavaType() == String.class) {
        return builder.like(builder.lower(path), "%" + criteria.getValue().toString().toLowerCase() + "%");
      }

      return builder.equal(path, criteria.getValue());
    }

    return null;
  }
}
