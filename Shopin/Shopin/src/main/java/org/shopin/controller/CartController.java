package org.shopin.controller;

import javax.validation.Valid;
import org.shopin.global.data.CategoryModel;
import org.shopin.pojo.CartItems;
import org.shopin.pojo.GenericMessage;
import org.shopin.service.IMessageSenderService;
import org.shopin.service.dao.IProductService;
import org.shopin.service.dao.IUserService;
import org.shopin.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class CartController extends CategoryModel {

    @Autowired
    IProductService productService;

    @Autowired
    private IMessageSenderService messageSenderService;

    @Autowired
    IUserService userService;

    @GetMapping("/cart")
    public String cartShow() {
        return "cart";
    }

    @PostMapping("/cart")
    public String cartShow(final @Valid @ModelAttribute("cartItems") CartItems cartItems,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            messageSenderService.sendMessage("mailbox", new GenericMessage("", "", "Malicious data: " + cartItems, MessageType.MALICIOUS));
            return "error/error";
        }

        redirectAttributes.addFlashAttribute("shoppingcart", productService.fetchCart(cartItems));

        return "redirect:/cart";
    }

    @ModelAttribute("cartItems")
    public CartItems getCartItems() {
        return new CartItems();
    }
}
