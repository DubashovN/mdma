package ru.filit.mdma.model.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.audit.kafka.AuditMessage;

@Service
@Slf4j
public class AuditService {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");

  @Value("auditfiles")
  private String auditDir;

  public void writeMessage(AuditMessage message) {
    File fileName = new File(auditDir, "dm-audit.txt");
    try (FileWriter fw = new FileWriter(fileName, StandardCharsets.UTF_8, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(String.format("%-19s %-8s %-15s %-1s%-29s %s",
          convertDateTime(message.getTimestamp()), message.getId(), message.getUserName(),
          message.getRequest(), message.getMethodName(), message.getBody()));
    } catch (IOException e) {
      log.error("Error [{}] while writing to file {}/dm-audit.txt", e.getMessage(), auditDir);
    }
  }

  private String convertDateTime(String timestamp) {
    Instant instant = Instant.ofEpochSecond(Long.parseLong(timestamp));
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    return dateTime.format(DATE_TIME_FORMATTER);
  }
}
