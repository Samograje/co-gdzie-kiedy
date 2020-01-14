package org.polsl.backend.filtering;

import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search<T> {
   public Specification<T> searchInitialization(String search){
    SpecificationsBuilder<T> builder = new SpecificationsBuilder<T>();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");
    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
    Specification<T> spec = builder.build();
    return spec;
  }
}
