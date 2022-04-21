package org.shopin.service;

import org.shopin.pojo.GenericMessage;
import static org.shopin.util.MessageType.PENDING;
import static org.shopin.util.MessageType.RESET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceProxy implements INotificationServiceProxy {

    @Autowired
    private INotificationService notificationService;

    @Override
    public void sendEmail(GenericMessage genericMessage) {
        switch (genericMessage.getType()) {
            case PENDING:
                notificationService.sendUserPendingRegistrationEmail(genericMessage.getReceiver());
                break;
            case RESET:
                notificationService.sendUserResetPasswordEmail(genericMessage.getReceiver(), genericMessage.getPayload());
                break;
            case MALICIOUS:
                notificationService.sendAdminErrorMessage(genericMessage.getPayload());
                break;
            case SUCCESS:
                notificationService.sendOrderMessage(genericMessage.getPayload());
                break;
        }
    }
}
