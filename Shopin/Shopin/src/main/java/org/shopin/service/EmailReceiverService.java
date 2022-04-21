package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiverService implements IMessageReceiverService {

    @Autowired
    INotificationServiceProxy notificationServiceProxy;

    @Override
    @JmsListener(destination = "mailbox", containerFactory = "totalDevFactory")
    public void receiveMessage(GenericMessage message) {
        notificationServiceProxy.sendEmail(message);
    }
}
