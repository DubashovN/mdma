package ru.filit.mdma.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.filit.mdma.model.audit.kafka.AuditMessage;
import ru.filit.mdma.model.service.AuditService;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value(value = "${system.element.queue.host}")
    private String addressHost;

    @Value(value = "${system.element.queue.port}")
    private String addressPort;

    @Value("${audit.kafka.group-id}")
    private String groupId;

    private final AuditService auditService;

    public KafkaConsumerConfig(AuditService auditService) {
        this.auditService = auditService;
    }

    public ConsumerFactory<String, AuditMessage> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, addressHost + ":" + addressPort);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
            new JsonDeserializer<>(AuditMessage.class));
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AuditMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AuditMessage> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    @KafkaListener(topics = "${audit.kafka.topic-name}",
        groupId = "${audit.kafka.group-id}",
        containerFactory = "kafkaListenerContainerFactory")
    public void listen(AuditMessage message) {
        log.info("Received Message in group {}: {}", groupId, message);
        auditService.writeMessage(message);
    }
}