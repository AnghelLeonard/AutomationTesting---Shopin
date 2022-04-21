package org.shopin.controller;

import java.util.List;
import org.shopin.global.data.CategoryModel;
import org.shopin.model.Product;
import org.shopin.service.dao.ICountService;
import org.shopin.service.dao.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController extends CategoryModel {

    @Autowired
    IProductService productService;

    @Autowired
    ICountService countService;

    @ModelAttribute("firstproducts")
    public List<Product> fetchFirstPageProducts() {
        return productService.fetchFirstPageProducts();
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("productscount", countService.fetchAllCount());
        return "index";
    }
}
