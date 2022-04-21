package org.shopin.service;

import org.springframework.stereotype.Service;

@Service
public interface INotificationService {

    public void sendUserPendingRegistrationEmail(final String address);

    public void sendUserResetPasswordEmail(final String address, final Object resetpassword);

    public void sendAdminErrorMessage(final String payload);

    public void sendOrderMessage(final String payload);
}
