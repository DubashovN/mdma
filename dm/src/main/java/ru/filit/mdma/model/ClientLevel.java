package ru.filit.mdma.model;

import java.math.BigDecimal;


public enum ClientLevel {
  LOW("Low"),
  MIDDLE("Middle"),
  SILVER("Silver"),
  GOLD("Gold");

  private final String value;
  private static final BigDecimal levelMiddle = BigDecimal.valueOf(30_000);
  private static final BigDecimal levelSilver = BigDecimal.valueOf(300_000);
  private static final BigDecimal levelGold = BigDecimal.valueOf(1_000_000);

  ClientLevel(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static ClientLevel fromAvgDailyBalance(BigDecimal adb) {
    if (adb.compareTo(levelGold) >= 0) {
      return ClientLevel.GOLD;
    } else if (adb.compareTo(levelSilver) >= 0) {
      return ClientLevel.SILVER;
    } else if (adb.compareTo(levelMiddle) >= 0) {
      return ClientLevel.MIDDLE;
    }
    return ClientLevel.LOW;
  }
}
