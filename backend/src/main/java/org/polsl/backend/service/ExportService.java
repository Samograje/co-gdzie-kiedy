package org.polsl.backend.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportService {

  public HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/pdf");
    return headers;
  }

  public InputStreamResource export(String type, List data) {
    try {
      if (data == null || data.isEmpty()) {
        return null;
      }

      //TODO: for, getFields
      List<Field> fields = Arrays.asList(data.get(0).getClass().getDeclaredFields());

      PdfPTable table = new PdfPTable(fields.size());
      table.setWidthPercentage(100);
      //table.setWidths();

      //PdfPCell cell;

      for (Field field : fields) {
        PdfPCell tableCell = new PdfPCell();
        tableCell.setPhrase(new Phrase(field.getName()));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setPaddingBottom(10);
        tableCell.setPaddingTop(5);
        table.addCell(tableCell);
      }

//      for(Field field: fields){
//        for(String columnField : field)
//      }

      Document document = new Document(PageSize.A4.rotate());
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      //PdfWriter writer = PdfWriter.getInstance(document,out);

      document.open();
      document.addCreationDate();
      document.addTitle(type);
      document.add(table);
      document.close();
      return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}