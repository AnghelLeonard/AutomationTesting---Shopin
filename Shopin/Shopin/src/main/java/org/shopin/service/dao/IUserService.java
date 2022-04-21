package org.shopin.service.dao;

import org.shopin.model.User;
import org.shopin.pojo.NewPassword;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    public void registerNewUser(final User user);

    public void activateNewUser(final String token);

    public void resetPasswordUser(final String address);

    public void newPasswordUser(final NewPassword newpassword);

    public String[] getUserAddressByEmail(final String email);
}
