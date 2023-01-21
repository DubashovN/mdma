package ru.filit.mdma.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;


@UtilityClass
public class DateTimeUtil {

  public static LocalDate getLoanPaymentDaySkipWeekends(LocalDate date, int deferment) {
    LocalDate result = date;
    int addedDays = 0;
    while (addedDays < deferment) {
      result = result.plusDays(1);
      if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY
          || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
        addedDays += 1;
      }
    }
    return result;
  }

  public static LocalDate getStartPeriod(LocalDate openDate) {
    LocalDate start;
    final LocalDate monthAgo = LocalDate.now().minusDays(30);
    if (openDate.compareTo(monthAgo) > 0) {
      start = openDate;
    } else {
      start = monthAgo;
    }
    return start;
  }

}
