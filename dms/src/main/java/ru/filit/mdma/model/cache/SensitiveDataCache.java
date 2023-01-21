package ru.filit.mdma.model.cache;


public interface SensitiveDataCache {

  boolean put(String key, String value);

  String read(String key);
}
