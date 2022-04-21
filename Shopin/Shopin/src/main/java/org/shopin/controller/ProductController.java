package org.shopin.controller;

import java.util.Optional;
import javax.validation.constraints.Pattern;
import org.shopin.global.data.CategoryModel;
import org.shopin.pojo.CartItems;
import org.shopin.service.dao.ICountService;
import org.shopin.service.dao.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.shopin.dto.ProductDto;

@Controller
@Validated
public class ProductController extends CategoryModel {

    @Value("${products.per.page}")
    private byte pagesize;

    @Autowired
    IProductService productService;

    @Autowired
    ICountService countService;

    @GetMapping("/products/{page:[0-9]+}")
    public String showAllProducts(final @PathVariable("page") String page,
            final @Pattern(regexp = "[a-zA-Z0-9 ]{3,100}") @RequestParam("search") Optional<String> search,
            final @Pattern(regexp = "[bfc]") @RequestParam("category") Optional<String> category,
            final @Pattern(regexp = "(im|in|ac)") @RequestParam("range") Optional<String> range,
            final @Pattern(regexp = "[0-9]{1,3}+") @RequestParam("id") Optional<String> id,
            final @Pattern(regexp = "^[a-zA-Z0-9_-]{1,100}$") @RequestParam("name") Optional<String> name,
            final @Pattern(regexp = "[a-z]{1}([_]{0}|[_]{1}[a-z]{1})") @RequestParam("fc") Optional<String> fc,
            final @Pattern(regexp = "(1_5)|(5_20)|(20_50)|(50_70)|(70_90)") @RequestParam("fd") Optional<String> fd,
            final @Pattern(regexp = "[1-9][0-9]{1,3}[_][1-9][0-9]{1,3}") @RequestParam("fp") Optional<String> fp,
            final @Pattern(regexp = "[0-9A-Zq-]{3,10}([_]{0}|[_]{1}[0-9A-Zq-]{3,10})") @RequestParam("fm") Optional<String> fm,
            final Model model) {

        if (search.isPresent()) {
            model.addAttribute("allproducts",
                    productService.fetchSearchedProducts(search.get(), Integer.valueOf(page)));
            model.addAttribute("productscount", null);
        } else {
            if (fc.isPresent() || fd.isPresent() || fp.isPresent() || fm.isPresent()) {
                model.addAttribute("allproducts",
                        productService.fetchFilteredProducts(fd, fp, fc, fm, category, range, id, name, Integer.valueOf(page)));
                model.addAttribute("productscount", null);
            } else {
                if (!category.isPresent() && !range.isPresent() && !id.isPresent() && !name.isPresent()) {
                    model.addAttribute("allproducts",
                            productService.fetchAllProducts(Integer.valueOf(page)));
                    model.addAttribute("productscount", countService.fetchAllCount());
                } else if (category.isPresent() && !range.isPresent() && !id.isPresent() && !name.isPresent()) {
                    model.addAttribute("allproducts",
                            productService.fetchCategoryProducts(Integer.valueOf(page), category.get()));
                    model.addAttribute("productscount", countService.fetchCategoryCount(category.get()));
                } else if (category.isPresent() && !range.isPresent() && id.isPresent() && name.isPresent()) {
                    model.addAttribute("allproducts",
                            productService.fetchSubcategoryProducts(Integer.valueOf(page), Long.valueOf(id.get())));
                    model.addAttribute("productscount", countService.fetchSubcategoryCount(Long.valueOf(id.get())));
                } else if (category.isPresent() && range.isPresent() && !id.isPresent() && !name.isPresent()) {
                    model.addAttribute("allproducts",
                            productService.fetchCategoryRangeProducts(Integer.valueOf(page), category.get(), range.get()));
                    model.addAttribute("productscount", countService.fetchCategoryRangeCount(category.get(), range.get()));
                }
            }
        }

        model.addAttribute("productsperpage", pagesize);
        model.addAttribute("maxallprice", countService.fetchAllTopPrice());

        return "products";
    }

    @GetMapping("/product")
    public String showProduct(final @Pattern(regexp = "[A-Za-z0-9]{10}+") @RequestParam("sku") String sku,
            final @Pattern(regexp = "^[a-zA-Z0-9_-]{1,100}$") @RequestParam("name") String name,
            final Model model) {

        ProductDto productDto = productService.fetchDetailBySku(sku);
        model.addAttribute("thisproduct", productService.fetchDetailBySku(sku));
        if (productDto != null) {
            model.addAttribute("thissku", productDto.getSku());
        }
        return "product";
    }

    @ModelAttribute("cartItems")
    public CartItems getCartItems() {
        return new CartItems();
    }
}
