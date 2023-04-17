package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloListener {

  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.QUEUE_NAME)
  public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
//    System.out.println("Message received: " + helloWorldMessage);
  }

  @JmsListener(destination = JmsConfig.SEND_RECEIVE_QUEUE)
  public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message)
      throws JMSException {

    HelloWorldMessage replyMessage = HelloWorldMessage.builder()
        .id(UUID.randomUUID())
        .message("World :)")
        .build();

    jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
//    System.out.println("Message received: " + helloWorldMessage);
  }
}
