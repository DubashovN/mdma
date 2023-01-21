package ru.filit.mdma.model.audit.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class MessageProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value(value = "${audit.kafka.topic-name}")
  private String topicName;

  @Autowired
  public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(String message) {

    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable ex) {
        log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
      }
    });
  }

}