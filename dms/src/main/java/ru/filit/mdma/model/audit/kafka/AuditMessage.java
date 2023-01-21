package ru.filit.mdma.model.audit.kafka;

import java.io.Serializable;
import java.time.Instant;
import java.util.OptionalInt;
import java.util.Random;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AuditMessage implements Serializable {

  private static final long serialVersionUID = 1L;

  private String timestamp;

  private String id;

  private String userName;

  private String methodName;

  private String body;

  private String request;

  public AuditMessage(String userName, String methodName, String body, boolean isRequest) {
    long timestamp = Instant.now().getEpochSecond();
    this.timestamp = String.valueOf(timestamp);
    this.id = generateId();
    this.userName = userName;
    this.methodName = methodName;
    this.body = body;
    if (isRequest) {
      this.request = ">";
    } else {
      this.request = "<";
    }
  }

  private String generateId() {
    final int min = 10_000_000;
    final int max = 99_999_999;
    Random random = new Random();
    OptionalInt optionalInt = random.ints(min, (max + 1)).findFirst();
    if (optionalInt.isPresent()) {
      return String.valueOf(optionalInt.getAsInt());
    }
    // TODO what if not present?
    return "12345678";
  }
}
