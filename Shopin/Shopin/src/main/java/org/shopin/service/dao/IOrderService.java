package org.shopin.service.dao;

import java.security.Principal;
import org.shopin.exceptions.ModifiedItemException;
import org.shopin.model.Address;
import org.shopin.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {

    public String fetchFinalCart(final String order) throws ModifiedItemException;

    public Address fetchLatestAddress(final String email);

    public String fetchLatestAddressOnly(final String email);

    public Address createNewAddress(final String email, final String address);

    public Address storeNewAddress(final String email, final String address);

    public Order storeNewOrder(final String email, String address, final String cart, final String total, final Principal principal);
}
