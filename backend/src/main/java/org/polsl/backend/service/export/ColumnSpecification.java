package org.polsl.backend.service.export;

class ColumnSpecification {

  private String columnField;
  private String columnName;

  /**
   * Informacje dotyczące kolumny tabeli.
   *
   * @param columnField techniczna nazwa pola
   * @param columnName czytelna nazwa pola
   */
  ColumnSpecification(String columnField, String columnName) {
    this.columnField = columnField;
    this.columnName = columnName;
  }

  String getColumnField() {
    return columnField;
  }

  String getColumnName() {
    return columnName;
  }
}
