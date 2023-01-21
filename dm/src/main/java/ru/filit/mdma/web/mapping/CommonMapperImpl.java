package ru.filit.mdma.web.mapping;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class CommonMapperImpl {

  public String asDto(BigDecimal in) {
    return (in == null) ? null : in.setScale(2, HALF_UP).stripTrailingZeros().toPlainString();
  }

  public Integer asInt(String in) {
    return Integer.parseInt(in);
  }

  public Long asEpochSeconds(LocalDateTime dateTime) {
    return dateTime.toEpochSecond(ZoneOffset.UTC);
  }

  public LocalDateTime asDateTime(Long epochSeconds) {
    Instant instant = Instant.ofEpochSecond(epochSeconds);
    return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
  }
}
