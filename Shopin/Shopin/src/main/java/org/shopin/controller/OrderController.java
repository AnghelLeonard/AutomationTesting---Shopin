package org.shopin.controller;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.shopin.exceptions.ModifiedItemException;
import org.shopin.model.Order;
import org.shopin.pojo.CartItems;
import org.shopin.pojo.GenericMessage;
import org.shopin.pojo.OrderJson;
import org.shopin.service.IMessageSenderService;
import org.shopin.service.dao.IOrderService;
import org.shopin.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Autowired
    private IMessageSenderService messageSenderService;

    @GetMapping("/purchase")
    public String showPurchase(HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        if (referer != null && referer.endsWith("/cart")) {
            return "purchase";
        }

        return "cart";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/ordermember")
    public String orderRoleShow(final @Pattern(regexp = "(true|false)") @RequestParam("addr") String addr,
            HttpServletRequest request, Model model, Principal principal) {

        String referer = request.getHeader("Referer");

        if ((referer != null && referer.endsWith("/cart"))
                || (referer != null && referer.endsWith("/login"))
                || (referer != null && referer.endsWith("/purchase"))) {

            if (addr.equals("false")) {
                String address = orderService.fetchLatestAddressOnly(principal.getName());
                model.addAttribute("userAddress", address);
            }

            return "order";
        }

        return "cart";
    }

    @GetMapping("/orderguest")
    public String orderGuestShow(HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        if (referer != null && referer.endsWith("/purchase")) {
            return "order";
        }

        return "cart";
    }

    @PostMapping("/done")
    public String gameOver(final @Valid @ModelAttribute("orderJson") OrderJson orderJson,
            final BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            messageSenderService.sendMessage("mailbox", new GenericMessage("", "", "Malicious data: "
                    + orderJson.getEmail() + "|" + orderJson.getOrder() + "|" + orderJson.getAddr() + "|" + orderJson.getNamesimg(), MessageType.MALICIOUS));
            return "error/error";
        }

        if (principal != null && !orderJson.getEmail().equals(principal.getName())) {
            return "error/error";
        }

        String total;
        try {
            total = orderService.fetchFinalCart(orderJson.getOrder());
        } catch (ModifiedItemException e) {
            return "cart";
        }

        Order order = orderService.storeNewOrder(orderJson.getEmail(), orderJson.getAddr(), orderJson.getOrder(), total, principal);

        if (order == null) {
            return "error/error";
        }

        messageSenderService.sendMessage("mailbox", new GenericMessage("", "", "|" + order.getId() + "|" + orderJson.getEmail() + "|"
                + orderJson.getOrder() + "|" + orderJson.getAddr() + "|" + orderJson.getAddrnr() + "|" + orderJson.getNamesimg() + "|" + total + "|",
                MessageType.SUCCESS));

        return "redirect:/thanks";
    }

    @ModelAttribute("orderJson")
    public OrderJson getOrderJson() {
        return new OrderJson();
    }

    @ModelAttribute("cartItems")
    public CartItems getCartItems() {
        return new CartItems();
    }
}
