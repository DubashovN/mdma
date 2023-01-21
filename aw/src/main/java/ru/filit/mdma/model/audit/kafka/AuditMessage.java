package ru.filit.mdma.model.audit.kafka;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuditMessage implements Serializable {

  private static final long serialVersionUID = 1L;

  private String timestamp;

  private String id;

  private String userName;

  private String methodName;

  private String body;

  private String request;

}