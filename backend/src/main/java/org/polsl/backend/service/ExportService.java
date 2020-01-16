package org.polsl.backend.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}