package org.polsl.backend.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportService {

  //TODO: gethttpheaders

  public InputStreamResource export(String type, List data){
    try{
      if(data == null || data.isEmpty()){
        return null;
      }

      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}