package org.polsl.backend.filtering;

import org.polsl.backend.entity.Software;
import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
   public static Specification<Software> searchInitialization(String search){
    SpecificationsBuilder builder = new SpecificationsBuilder();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");
    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
    Specification<Software> spec = builder.build();
    return spec;
  }
}
