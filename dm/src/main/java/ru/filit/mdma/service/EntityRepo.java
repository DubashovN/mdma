package ru.filit.mdma.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class EntityRepo {

  private static final Logger log = LoggerFactory.getLogger(EntityRepo.class);

  private final ObjectMapper objectMapper = new ObjectMapper(
      new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  public void clearContents(String filename) throws FileNotFoundException {
    new PrintWriter(filename).close();
  }

  public <T> void writeList(String filename, List<T> entities) {
    try {
      File file = ResourceUtils.getFile(filename);
      try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
        objectMapper.writeValue(out, entities);
        log.info(filename + " written");
      } catch (IOException e) {
        log.info("Can't save entities in {}", filename);
      }
    } catch (FileNotFoundException e) {
      log.info("File {} not found", filename);
    }
  }

  public <T> List<T> readList(String filename, TypeReference<List<T>> tr) {
    try {
      File file = ResourceUtils.getFile(filename);
      return objectMapper.readValue(file, tr);
    } catch (IOException e) {
      log.info("Can't read entities from {}", filename);
      return Collections.emptyList();
    }
  }
}
