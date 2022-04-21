package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public interface IMessageSenderService {

    public void sendMessage(final String destination, final GenericMessage message);
}
