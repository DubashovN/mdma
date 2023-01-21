package ru.filit.mdma.web;

import java.util.UUID;
import lombok.experimental.UtilityClass;


@UtilityClass
public final class TokenUtil {

  public static String buildToken() {
    String token = UUID.randomUUID().toString().replace("-", "");
    return "#" + token + "#";
  }

  public static boolean isTokenized(String guid) {
    return guid.length() > 2 && guid.startsWith("#") && guid.endsWith("#");
  }
}
