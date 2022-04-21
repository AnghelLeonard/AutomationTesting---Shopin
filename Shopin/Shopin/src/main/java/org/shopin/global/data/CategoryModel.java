package org.shopin.global.data;

import java.util.List;
import org.shopin.dto.CategoryDto;
import org.shopin.pojo.CartItems;
import org.shopin.service.dao.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class CategoryModel {

    @Autowired
    ICategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoryDto> fetchAllCategories() {
        return categoryService.fetchAllCategories();
    }
    
    @ModelAttribute("cartItems")
    public CartItems getCartItems() {
        return new CartItems();
    }
}
