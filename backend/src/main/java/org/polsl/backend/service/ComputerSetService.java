package org.polsl.backend.service;

import org.polsl.backend.repository.ComputerSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerSetService {
  private ComputerSetRepository computerSetRepository;

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository) {
    this.computerSetRepository = computerSetRepository;
  }
}
