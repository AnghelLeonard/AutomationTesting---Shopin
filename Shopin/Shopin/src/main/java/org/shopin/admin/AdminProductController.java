package org.shopin.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.shopin.pojo.GenericMessage;
import org.shopin.service.IMessageSenderService;
import org.shopin.service.dao.ICategoryService;
import org.shopin.service.dao.ICountService;
import org.shopin.util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
@RequestMapping("/x10qerK0")
public class AdminProductController {

    @Autowired
    private IAdminProduct adminProductService;

    @Autowired
    private ICountService countService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IMessageSenderService messageSenderService;

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProduct(final @Valid @ModelAttribute("newProduct") NewProduct newProduct,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            messageSenderService.sendMessage("mailbox", new GenericMessage("", "", "Malicious data: " + newProduct, MessageType.MALICIOUS));
            return "error/error";
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(newProduct.getProduct());

            JsonNode node = (JsonNode) items.elements().next();

            adminProductService.addProduct(node);
            addProductAction(node);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        redirectAttributes.addFlashAttribute("success", "SUCCESS");

        return "redirect:add";
    }

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyProduct(final @Valid @ModelAttribute("newProduct") NewProduct newProduct, final String live,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            messageSenderService.sendMessage("mailbox", new GenericMessage("", "", "Malicious data: " + newProduct, MessageType.MALICIOUS));
            return "error/error";
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(newProduct.getProduct());

            JsonNode node = (JsonNode) items.elements().next();

            adminProductService.modifyProduct(node);

            if (live.equals("false,1")) {
                addProductAction(node);
            }

            if (live.equals("true,0")) {
                reviveProductAction(node);
            }

            if (live.equals("true,1")) {
                reviveHigherPriceAction(node);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        redirectAttributes.addFlashAttribute("success", "SUCCESS");

        return "redirect:modify";
    }

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    public String switchUser(final String email, final boolean status, final RedirectAttributes redirectAttributes) {

        adminProductService.updateUser(email, status);

        redirectAttributes.addFlashAttribute("success", "SUCCESS");

        return "redirect:switch";
    }

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProduct(Model model) {

        model.addAttribute("product", "");

        return "admin/add";
    }

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyProduct(final @Pattern(regexp = "[0-9A-Za-z]{10}") @RequestParam("sku") Optional<String> sku, Model model) {

        if (sku.isPresent()) {
            model.addAttribute("product", adminProductService.loadProduct(sku.get()));
        } else {
            model.addAttribute("product", "");
        }

        return "admin/modify";
    }

    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    public String switchUser(Model model) {

        model.addAttribute("email", "");
        model.addAttribute("status", "");

        return "admin/switch";
    }

    private void addProductAction(JsonNode node) throws IOException {
        if ("true".equals(node.get("live").asText())) {
            adminProductService.evictAllCategories();
            categoryService.fetchAllCategories();
            adminProductService.incFetchAllCount(countService.fetchAllCount());
            adminProductService.incFetchCategoryCount(node.get("mcat").asText(), countService.fetchCategoryCount(node.get("mcat").asText()));
            adminProductService.incFetchCategoryRangeCount(node.get("mcat").asText(), node.get("scat").asText(),
                    countService.fetchCategoryRangeCount(node.get("mcat").asText(), node.get("scat").asText()));
            adminProductService.incFetchSubcategoryCount(Long.valueOf(node.get("categoryid").asText()), countService.fetchSubcategoryCount(Long.valueOf(node.get("categoryid").asText())));
            adminProductService.incFetchAllTopPrice(Float.valueOf(node.get("price").asText()), countService.fetchAllTopPrice());
        }
    }

    private void reviveProductAction(JsonNode node) throws IOException {
        if ("false".equals(node.get("live").asText())) {
            adminProductService.evictAllCategories();
            categoryService.fetchAllCategories();
            adminProductService.decFetchAllCount(countService.fetchAllCount());
            adminProductService.decFetchCategoryCount(node.get("mcat").asText(), countService.fetchCategoryCount(node.get("mcat").asText()));
            adminProductService.decFetchCategoryRangeCount(node.get("mcat").asText(), node.get("scat").asText(),
                    countService.fetchCategoryRangeCount(node.get("mcat").asText(), node.get("scat").asText()));
            adminProductService.decFetchSubcategoryCount(Long.valueOf(node.get("categoryid").asText()), countService.fetchSubcategoryCount(Long.valueOf(node.get("categoryid").asText())));
            adminProductService.decFetchAllTopPrice(Float.valueOf(node.get("price").asText()), countService.fetchAllTopPrice());
        }
    }

    private void reviveHigherPriceAction(JsonNode node) throws IOException {
        adminProductService.evictAllCategories();
        categoryService.fetchAllCategories();
        adminProductService.incFetchAllTopPrice(Float.valueOf(node.get("price").asText()), countService.fetchAllTopPrice());
    }
}
