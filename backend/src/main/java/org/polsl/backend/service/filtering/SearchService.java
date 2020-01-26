package org.polsl.backend.service.filtering;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Serwis udostępniający funkcjonalność tworzenia specyfikacji filtrowania.
 */
public class SearchService {

  /**
   * Tworzy obiekt specyfikacji filtrowania na podstawie podanych parametrów.
   *
   * @param searchQuery    parametr zapytania url z danymi dot filtrowania
   * @param searchOperator operator łączenia warunków filtrowania
   * @param typeClass      klasa implementująca {@link SearchSpecification} zawierająca mapowania nazw dto na nazwy pól encji
   * @param <T>            klasa encji bazodanowej, której dotyczy wyszukiwanie
   * @return obiekt {@link Specification} zawierający dane dotyczące filtrowania
   */
  public static <T> Specification<T> getSpecification(String searchQuery, String searchOperator, Class typeClass) {

    // uzyskanie parametrów wyszukiwania
    Pattern pattern = Pattern.compile("(\\w*)([:<>])([\\w/ ]*),");
    Matcher matcher = pattern.matcher(searchQuery + ",");
    List<SearchCriteria> params = new ArrayList<>();
    while (matcher.find()) {
      params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
    }

    // uzyskanie operatora wyszukiwania
    SearchOperator operator;
    if (searchOperator != null) {
      try {
        operator = SearchOperator.valueOf(searchOperator.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Podany operator wyszukiwania jest nieprawidłowy");
      }
    } else {
      operator = SearchOperator.AND;
    }

    // budowanie specyfikacji
    return new SpecificationBuilder<T>()
        .typeClass(typeClass)
        .params(params)
        .operator(operator)
        .build();
  }
}
