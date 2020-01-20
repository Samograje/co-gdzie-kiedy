package org.polsl.backend.service.export;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class ExportService {

  private static Font headFont;
  private static Font tableHeaderFont;
  private static Font tableBodyFont;
  private static Font titleFont;
  private static HyphenationAuto hyphenation;

  public ExportService() throws IOException, DocumentException {
    BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    headFont = new Font(baseFont, -1.0F, 1);
    tableHeaderFont = new Font(baseFont, 12, 1);
    tableBodyFont = new Font(baseFont, 10);
    titleFont = new Font(baseFont, 18, 1);
    hyphenation = new HyphenationAuto("pl_PL", "PL", 2, 2);
  }

  /**
   * Uzyskuje nagłówki http.
   *
   * @return nagłówki http
   */
  public HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/pdf");
    return headers;
  }

  /**
   * Eksportuje podane dane do pdf.
   *
   * @param title tytuł dokumentu
   * @param data  dane do tabeli
   * @return plik pdf
   */
  public InputStreamResource export(String title, List data) {

    if (data == null || data.isEmpty()) {
      return null;
    }

    List<ColumnSpecification> columnSpecifications = getDataModel(data);

    PdfPTable table = new PdfPTable(columnSpecifications.size());
    table.setWidthPercentage(100);

    // nagłówek
    for (ColumnSpecification columnSpecification : columnSpecifications) {
      PdfPCell tableCell = createTableCell(columnSpecification.getColumnName(), tableHeaderFont, false);
      tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      tableCell.setPaddingBottom(10);
      tableCell.setPaddingTop(5);
      table.addCell(tableCell);
    }

    // tabela
    List<String> columnFields = columnSpecifications.stream()
      .map(ColumnSpecification::getColumnField)
      .collect(Collectors.toList());
    List<Map<String, String>> mappedData = getData(data);
    for (Map<String, String> mappedItem : mappedData) {
      for (String columnField : columnFields) {
        PdfPCell tableCell = createTableCell(mappedItem.get(columnField), tableBodyFont, true);
        tableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(tableCell);
      }
    }

    Document document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      PdfWriter writer = PdfWriter.getInstance(document, out);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    document.open();
    try {
      document.add(createHeader());
      document.add(createTitle(title));
      document.add(table);
    } catch (DocumentException e) {
      throw new RuntimeException();
    }
    document.close();
    return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
  }

  private Element createHeader() {
    return new Phrase("Co gdzie kiedy", headFont);
  }

  private Element createTitle(String title) {
    Paragraph titleParagraph = new Paragraph();
    titleParagraph.setSpacingAfter(10);
    titleParagraph.setAlignment(Element.ALIGN_CENTER);
    titleParagraph.add(new Phrase(title, titleFont));
    return titleParagraph;
  }

  private PdfPCell createTableCell(String value, Font font, Boolean hyphenateWords) {
    if (value == null) {
      return new PdfPCell();
    }

    if (hyphenateWords) {
      Chunk chunk = new Chunk(value, font);
      chunk.setHyphenation(hyphenation);
      return new PdfPCell(new Paragraph(chunk));
    }

    return new PdfPCell(new Phrase(value, font));
  }

  /**
   * Uzyskuje informacje o kolumnach tabeli do eksportu
   *
   * @param data lista z danymi do eksportu
   * @return lista z informacjami o kolumnach
   */
  private List<ColumnSpecification> getDataModel(List data) {
    if (data == null || data.isEmpty()) {
      return null;
    }

    List<Field> fields = Arrays.asList(data.get(0).getClass().getDeclaredFields());
    return fields.stream()
      .filter(field -> field.isAnnotationPresent(ExportColumn.class))
      .map(field -> {
        String name = field.getAnnotation(ExportColumn.class).value();
        return new ColumnSpecification(field.getName(), name);
      })
      .collect(Collectors.toList());
  }

  /**
   * Mapuje listę danych na format łatwiejszy do odczytu
   *
   * @param data lista z danymi do eksportu
   * @return lista danych w postaci listy map
   */
  private List<Map<String, String>> getData(List data) {
    if (data == null || data.isEmpty()) {
      return null;
    }

    // uzyskiwanie informacji o polach klasy
    Map<String, Field> fields = Arrays.stream(data.get(0).getClass().getDeclaredFields())
      .filter(field -> field.isAnnotationPresent(ExportColumn.class))
      .peek(field -> field.setAccessible(true))
      .collect(LinkedHashMap::new, (map, item) -> map.put(item.getName(), item), Map::putAll);

    // uzyskiwanie danych z pól klasy
    List<Map<String, String>> mappedData = new ArrayList<>();
    for (Object item : data) {
      Map<String, String> itemData = new LinkedHashMap<>();
      for (Map.Entry<String, Field> fieldEntry : fields.entrySet()) {

        // uzyskanie wartości pola
        Object fieldValue;
        try {
          fieldValue = fieldEntry.getValue().get(item);
        } catch (IllegalAccessException e) {
          return null;
        }

        // przypadek, gdy wartością jest kolekcja
        if (fieldValue instanceof Iterable) {
          Iterable<?> items = (Iterable<?>) fieldValue;
          StringJoiner stringJoiner = new StringJoiner("\n");
          items.forEach(i -> stringJoiner.add(i.toString()));
          fieldValue = stringJoiner;
        }

        // dodanie wartości do mapy
        if (fieldValue != null) {
          fieldValue = fieldValue.toString();
        }
        itemData.put(fieldEntry.getKey(), (String) fieldValue);
      }
      mappedData.add(itemData);
    }

    return mappedData;
  }
}
