package org.shopin.service.dao;

import java.util.List;
import org.shopin.dao.CategoryRepository;
import org.shopin.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CountService implements ICountService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    @Cacheable(cacheNames = "count_all", sync = true, key = "'count_all'")
    public long fetchAllCount() {
        return productRepository.fetchAllCount();
    }

    @Override
    @Cacheable(cacheNames = "count_all", sync = true, key = "'count_'+#key")
    public long fetchCategoryCount(String key) {
        return productRepository.fetchCategoryAndRangeCount("/" + key + "/%");
    }

    @Override
    @Cacheable(cacheNames = "count_all", sync = true, key = "'count_'+#key1+'_'+#key2")
    public long fetchCategoryRangeCount(String key1, String key2) {
        return productRepository.fetchCategoryAndRangeCount("/" + key1 + "/" + key2 + "/");
    }

    @Override
    @Cacheable(cacheNames = "count_all", sync = true, key = "'count_subcategory_'+#key")
    public long fetchSubcategoryCount(long key) {
        return productRepository.fetchSubcategoryCount(key);
    }

    @Override
    @Cacheable(cacheNames = "count_all", sync = true, key = "'price_all'")
    public Float fetchAllTopPrice() {
        return productRepository.fetchAllPrice();
    }

    @Override
    @Transactional
    public void fetchAllColorsByCategory() {
        long[] ids = categoryRepository.fetchAllCategoriesIds();

        for (long id : ids) {
            List<String> colors = productRepository.fetchAllColorsByCategory(id);
            if (colors != null && colors.size() > 0) {
                categoryRepository.insertCategoryColors(colors.stream().reduce("", (c, e) -> c + "," + e).substring(1), id);
            }
        }
    }

    @Override
    @Transactional
    public void fetchAllPricesCategory() {
        long[] ids = categoryRepository.fetchAllCategoriesIds();

        for (long id : ids) {
            Float maxprice = productRepository.fetchAllPricesByCategory(id);
            if (maxprice != null && maxprice > 0.0f) {
                categoryRepository.insertCategoryPrices(maxprice, id);
            }
        }
    }
}
