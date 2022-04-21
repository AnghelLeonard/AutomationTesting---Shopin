package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderService implements IMessageSenderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MessageSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendMessage(final String destination, final GenericMessage message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
