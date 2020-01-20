package org.polsl.backend.service.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExportColumn {
  String field(); // techniczna nazwa pola
  String name(); // czytelna nazwa pola
}
