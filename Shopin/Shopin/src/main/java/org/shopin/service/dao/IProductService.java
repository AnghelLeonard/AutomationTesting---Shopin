package org.shopin.service.dao;

import java.util.List;
import java.util.Optional;
import org.shopin.dto.FilterProductDto;
import org.shopin.dto.LightweightProductDto;
import org.shopin.model.Product;
import org.shopin.pojo.CartItems;
import org.springframework.stereotype.Service;
import org.shopin.dto.ProductDto;

@Service
public interface IProductService {

    List<Product> fetchFirstPageProducts();

    List<Product> fetchAllProducts(final int page);

    List<Product> fetchCategoryProducts(final int page, final String category);

    List<Product> fetchCategoryRangeProducts(final int page, final String category, final String range);

    List<Product> fetchSubcategoryProducts(final int page, final long id);

    ProductDto fetchDetailBySku(final String sku);

    List<FilterProductDto> fetchFilteredProducts(final Optional<String> fd, final Optional<String> fp,
            final Optional<String> fc, final Optional<String> fm, final Optional<String> category, final Optional<String> range, final Optional<String> id, final Optional<String> name, int page);

    List<FilterProductDto> fetchSearchedProducts(final String like, final int page);

    List<LightweightProductDto> fetchCart(final CartItems cartItems);
}
