package org.shopin.service.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import org.shopin.dao.AddressRepository;
import org.shopin.dao.OrderRepository;
import org.shopin.dao.ProductRepository;
import org.shopin.dto.SuperLightweightProductDto;
import org.shopin.exceptions.ModifiedItemException;
import org.shopin.model.Address;
import org.shopin.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderService implements IOrderService {

    @Value("${products.cart.maxsize}")
    private int cartmaxsize;

    @Value("${products.cart.maxcost}")
    private int cartmaxcost;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public String fetchFinalCart(final String order) throws ModifiedItemException {

        double total = 0.0d;
        int quantity = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(order);

            Iterator it = items.elements();

            while (it.hasNext()) {
                JsonNode node = (JsonNode) it.next();
                quantity = quantity + node.get("quantity").asInt();

                if (quantity <= cartmaxsize) {
                    SuperLightweightProductDto superLightweightProductDto = productRepository.fetchSuperLightweightProductBySku(node.get("msku").asText());

                    float price = superLightweightProductDto.getPrice() - (superLightweightProductDto.getPrice() * superLightweightProductDto.getDiscount()) / 100;
                    total = total + price * node.get("quantity").asInt();

                    if (Math.abs(node.get("price").asDouble() - price) >= 0.5 || total > cartmaxcost) {
                        throw new ModifiedItemException();
                    }

                    if (!superLightweightProductDto.getSizes().contains(node.get("msize").asText())) {
                        throw new ModifiedItemException();
                    }

                    if (!superLightweightProductDto.getLive()) {
                        throw new ModifiedItemException();
                    }
                } else {
                    throw new ModifiedItemException();
                }

            }
        } catch (IOException e) {
            throw new ModifiedItemException(e);
        }

        return new DecimalFormat("#0.00").format(total);
    }

    @Override
    public Address storeNewAddress(final String email, final String address) {

        Address newAddress = new Address();

        newAddress.setAddress(address);
        newAddress.setEmail(email);
        newAddress.setCreatedon(new Date());

        return addressRepository.saveAndFlush(newAddress);
    }

    @Override
    @Transactional
    public Order storeNewOrder(final String email, String address, final String cart, final String total, final Principal principal) {

        address = cleanUp(address);

        Order newOrder = new Order();
        newOrder.setCart(cart);
        newOrder.setEmail(email);
        newOrder.setTotal(total);
        newOrder.setCreatedon(new Date());

        if (principal == null) {
            newOrder.setAddress(null);
        } else {
            Address fetchedAddress = fetchLatestAddress(email);
            if (fetchedAddress == null || !fetchedAddress.getAddress().equals(address)) {
                Address newAddress = createNewAddress(email, address);
                newAddress = addressRepository.save(newAddress);
                newOrder.setAddress(newAddress);
            } else {
                newOrder.setAddress(fetchedAddress);
            }
        }
        return orderRepository.saveAndFlush(newOrder);
    }

    @Override
    public Address fetchLatestAddress(final String email) {

        return addressRepository.fetchLatestAddress(email);
    }

    @Override
    public String fetchLatestAddressOnly(final String email) {
        return addressRepository.fetchLatestAddressOnly(email);
    }

    @Override
    public Address createNewAddress(final String email, final String address) {

        Address newAddress = new Address();

        newAddress.setAddress(address);
        newAddress.setEmail(email);
        newAddress.setCreatedon(new Date());

        return newAddress;
    }

    private String cleanUp(String address) {

        String start = address.substring(0, address.indexOf(",\"infol\":\"") + 10);
        String end = address.substring(address.indexOf(",\"infol\":\"") + 10);
        address = start + end.substring(end.indexOf("\""));
        if (address.contains(",\"infof\":\"")) {
            int nclf = address.indexOf(",\"infof\":\"") + 10;
            address = address.substring(0, nclf) + "\"}";
        }

        return address;
    }

}
