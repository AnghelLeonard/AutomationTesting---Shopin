package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public interface INotificationServiceProxy {

    public void sendEmail(GenericMessage genericMessage);
}
