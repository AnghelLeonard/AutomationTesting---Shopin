package org.shopin.config;

import org.shopin.dao.CategoryRepository;
import org.shopin.service.dao.ICountService;
import org.shopin.service.dao.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class InitializationConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ICountService countService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // fetch counts
        countService.fetchAllCount();
        countService.fetchCategoryCount("b");
        countService.fetchCategoryCount("f");
        countService.fetchCategoryCount("c");
        countService.fetchCategoryRangeCount("b", "im");
        countService.fetchCategoryRangeCount("b", "in");
        countService.fetchCategoryRangeCount("b", "ac");
        countService.fetchCategoryRangeCount("f", "im");
        countService.fetchCategoryRangeCount("f", "in");
        countService.fetchCategoryRangeCount("f", "ac");
        countService.fetchCategoryRangeCount("c", "im");
        countService.fetchCategoryRangeCount("c", "in");
        countService.fetchCategoryRangeCount("c", "ac");
        long[] ids = categoryRepository.fetchAllCategoriesIds();
        for (long id : ids) {
            countService.fetchSubcategoryCount(id);
        }

        // fetch colors
        countService.fetchAllColorsByCategory();

        // fetch prices
        countService.fetchAllPricesCategory();

        // fetch biggest price
        countService.fetchAllTopPrice();

        // fetch categories
        categoryService.fetchAllCategories();
    }
}
