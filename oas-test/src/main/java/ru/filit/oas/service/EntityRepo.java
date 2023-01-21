package ru.filit.oas.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Collections;
import java.util.List;

@Component
public class EntityRepo {

    private static final Logger log = LoggerFactory.getLogger(EntityService.class);

    private final ObjectMapper objectMapper = new ObjectMapper(
            new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
