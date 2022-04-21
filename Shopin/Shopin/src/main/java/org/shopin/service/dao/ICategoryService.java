package org.shopin.service.dao;

import java.util.List;
import org.shopin.dto.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface ICategoryService {

    public List<CategoryDto> fetchAllCategories();
}
