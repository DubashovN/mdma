package ru.filit.mdma.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

  @Value("${system.element.dm.host}")
  private String baseHost;

  @Value("${system.element.dm.port}")
  private String basePort;

  @Value("http://")
  private String protocol;

  @Value("/dm")
  private String contextPath;

  @Bean
  public WebClient webClientWithTimeout() {
    return WebClient.builder()
        .baseUrl(buildBaseUrl())
        .build();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  private String buildBaseUrl() {
    return protocol + baseHost + ":" + basePort + contextPath;
  }
}