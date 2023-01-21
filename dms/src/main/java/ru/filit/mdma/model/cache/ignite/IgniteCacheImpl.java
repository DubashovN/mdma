package ru.filit.mdma.model.cache.ignite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.cache.SensitiveDataCache;

/**
 * @author A.Khalitova 06-Dec-2021
 */
@Service
public class IgniteCacheImpl implements SensitiveDataCache {

  private final IgniteDmsClient igniteDmsClient;

  @Autowired
  public IgniteCacheImpl(IgniteDmsClient igniteDmsClient) {
    this.igniteDmsClient = igniteDmsClient;
  }

  @Override
  public boolean put(String key, String value) {
    return igniteDmsClient.getCache().putIfAbsent(key, value);
  }

  @Override
  public String read(String key) {
    return igniteDmsClient.getCache().get(key);
  }
}
