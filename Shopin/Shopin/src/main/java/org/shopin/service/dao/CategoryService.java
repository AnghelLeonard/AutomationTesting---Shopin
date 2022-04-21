package org.shopin.service.dao;

import java.util.List;
import org.shopin.dao.CategoryRepository;
import org.shopin.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Cacheable(cacheNames = "categories", sync = true, key = "'categories'")
    public List<CategoryDto> fetchAllCategories() {
        return categoryRepository.fetchAllCategories();
    }
}
