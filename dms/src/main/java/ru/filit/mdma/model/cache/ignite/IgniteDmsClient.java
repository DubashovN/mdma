package ru.filit.mdma.model.cache.ignite;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author A.Khalitova 06-Dec-2021
 */
@Service
@Slf4j
public class IgniteDmsClient {

  @Value("cache")
  private String cacheName;

  private final Ignite ignite;

  @Getter
  private IgniteCache<String, String> cache;

  public IgniteDmsClient(Ignite ignite) {
    this.ignite = ignite;
  }

  @PostConstruct
  public void init() {
    cache = ignite.cache(cacheName);
    log.info("Ignite cache has started in {}", this.getClass().getSimpleName());
  }

  @PreDestroy
  public void destroy() {
    if (ignite != null) {
      ignite.close();
    }
    log.info("Bean {} is destroyed", this.getClass().getSimpleName());
  }
}
