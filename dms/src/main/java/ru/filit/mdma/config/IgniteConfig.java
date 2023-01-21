package ru.filit.mdma.config;

import java.util.Arrays;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

  @Bean
  public IgniteConfigurer nodeConfigurer() {
    return cfg -> {
      //Setting some property.
      //Other will come from `application.yml`
      cfg.setIgniteInstanceName("igniteInstance");
      CacheConfiguration<String, String> cacheCfg = new CacheConfiguration<>();
      cacheCfg.setName("cache");
      cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.THIRTY_MINUTES));

      TcpDiscoverySpi spi = new TcpDiscoverySpi();
      TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

      ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
      spi.setIpFinder(ipFinder);

      TcpCommunicationSpi tcpCommunicationSPI = new TcpCommunicationSpi();
      tcpCommunicationSPI.setMessageQueueLimit(1024);

      cfg.setCommunicationSpi(tcpCommunicationSPI);
      cfg.setDiscoverySpi(spi);
      cfg.setCacheConfiguration(cacheCfg);
    };
  }
}