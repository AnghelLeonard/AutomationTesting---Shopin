package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public interface IMessageReceiverService {

    public void receiveMessage(final GenericMessage message);
}
