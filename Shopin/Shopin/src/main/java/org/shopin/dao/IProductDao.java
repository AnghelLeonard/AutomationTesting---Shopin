package org.shopin.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.shopin.dto.FilterProductDto;
import org.shopin.model.Product;

public interface IProductDao<T, ID extends Serializable> {

    List<T> fetchFirstPageProducts();

    List<T> fetchAllProducts(final int page);

    List<FilterProductDto> fetchFilteredProducts(final Optional<String> fd, final Optional<String> fp,
            final Optional<String> fc, final Optional<String> fm, final Optional<String> category, final Optional<String> range, final Optional<String> id, final Optional<String> name, int page);

    List<FilterProductDto> fetchSearchedProducts(final String like, final int page);

    List<Product> fetchCategoryProducts(final int page, final String category);

    List<Product> fetchCategoryRangeProducts(final int page, final String category, final String range);

    List<Product> fetchSubcategoryProducts(final int page, final long id);
}
