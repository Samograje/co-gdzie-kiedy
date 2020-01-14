package org.polsl.backend.filtering;

import org.polsl.backend.entity.ComputerSet;
import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search<T> {
  private T object;
  private String search;

  public Search(T object, String search){
    this.object = object;
    this.search = search;
  }

  public Specification<T> searchInitialization(){
    if(object instanceof ComputerSet){
      computerSetSearchValueToEntityValuesParser();
    }

    SpecificationsBuilder<T> builder = new SpecificationsBuilder<>();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");
    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
    Specification<T> spec = builder.build();
    return spec;
  }

  private void computerSetSearchValueToEntityValuesParser(){
    if(search == null)
      return;
    if(search.contains("computerSetInventoryNumber")) {
      search = search.replace("computerSetInventoryNumber", "inventoryNumber");
    }
  }
}
