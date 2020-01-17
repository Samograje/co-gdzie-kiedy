package org.polsl.backend.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportService {

  public HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/pdf");
    return headers;
  }

  public InputStreamResource export(String title, List data) {
    try {
      if (data == null || data.isEmpty()) {
        return null;
      }

      //TODO: for, getFields
      List<Field> fields = Arrays.asList(data.get(0).getClass().getDeclaredFields());


      //ustaw domyślne
      PdfPTable header = new PdfPTable(1);
      header.setWidthPercentage(100);
      header.getDefaultCell().setFixedHeight(40);
      header.getDefaultCell().setBorder(Rectangle.BOTTOM);

      //dodaj nagłówek
      PdfPCell text = new PdfPCell();
      text.setPaddingBottom(15);
      text.setPaddingLeft(10);
      text.setBorder(Rectangle.BOTTOM);
      text.addElement(new Phrase(title));
      header.addCell(text);


      //dodaj nagłówki kolumn
      PdfPTable table = new PdfPTable(fields.size());
      table.setWidthPercentage(100);

      for (Field field : fields) {
        PdfPCell tableCell = new PdfPCell();
        tableCell.setPhrase(new Phrase(field.getName()));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setPaddingBottom(10);
        tableCell.setPaddingTop(5);
        table.addCell(tableCell);
      }

      //dodaj zawartość komórek
      //TODO: jak zrzutować dane? :(
      for (Field field : fields) {
        PdfPCell valueCell = new PdfPCell();
        valueCell.setPhrase(new Phrase(data.get(0).getClass().toString()));
        valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        valueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(valueCell);
      }

      //dodaj stopkę
      PdfPTable footer = new PdfPTable(1);
      footer.setWidthPercentage(100);
      footer.getDefaultCell().setFixedHeight(40);
      footer.getDefaultCell().setBorder(Rectangle.TOP);
      footer.addCell(new Phrase("Co-gdzie-kiedy; data wygenerowania dokumentu: " + LocalDateTime.now()));


      //utwórz dokument i zapisz dane
      Document document = new Document(PageSize.A4.rotate());
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      PdfWriter writer = PdfWriter.getInstance(document, out);

      document.open();
      document.addCreationDate();
      document.addTitle(title);
      document.add(header);
      document.add(table);
      document.add(footer);
      document.close();
      return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return null;
  }
}