package ru.filit.mdma.repository.impl;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import ru.filit.mdma.web.exception.InvalidDataException;


@Slf4j
public abstract class AbstractYmlRepository {

  protected String getFile(String filePath, String fileName) {
    File file = new File(filePath, fileName);
    if (!file.isFile()) {
      log.info("File [{}] in dir [{}] doesn't exist",
          fileName, filePath);
      throw new InvalidDataException("File doesn't exist");
    }
    return file.getPath();
  }
}
