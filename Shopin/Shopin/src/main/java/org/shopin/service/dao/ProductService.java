package org.shopin.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.shopin.dao.DetailRepository;
import org.shopin.dao.IProductDao;
import org.shopin.dao.ProductRepository;
import org.shopin.dto.FilterProductDto;
import org.shopin.dto.LightweightProductDto;
import org.shopin.model.Product;
import org.shopin.pojo.CartItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.shopin.dto.ProductDto;

@Repository
public class ProductService implements IProductService {

    @Value("${products.cart.maxsize}")
    private int cartmaxsize;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private IProductDao productDao;

    @Override
    public List<Product> fetchFirstPageProducts() {
        return productDao.fetchFirstPageProducts();
    }

    @Override
    public List<Product> fetchAllProducts(int page) {
        return productDao.fetchAllProducts(page);
    }

    @Override
    public List<Product> fetchCategoryProducts(int page, String category) {
        return productDao.fetchCategoryProducts(page, category);
    }

    @Override
    public List<Product> fetchCategoryRangeProducts(int page, String category, String range) {
        return productDao.fetchCategoryRangeProducts(page, category, range);
    }

    @Override
    public List<Product> fetchSubcategoryProducts(int page, long id) {
        return productDao.fetchSubcategoryProducts(page, id);
    }

    @Override
    public ProductDto fetchDetailBySku(String sku) {
        return detailRepository.fetchDetailBySku(sku);
    }

    @Override
    public List<FilterProductDto> fetchFilteredProducts(final Optional<String> fd, final Optional<String> fp,
            final Optional<String> fc, final Optional<String> fm, final Optional<String> category, final Optional<String> range, final Optional<String> id, final Optional<String> name, int page) {
        return productDao.fetchFilteredProducts(fd, fp, fc, fm, category, range, id, name, page);
    }

    @Override
    public List<FilterProductDto> fetchSearchedProducts(final String like, final int page) {
        return productDao.fetchSearchedProducts(like, page);
    }

    @Override
    public List<LightweightProductDto> fetchCart(final CartItems cartItems) {

        List<LightweightProductDto> products = new ArrayList<>();
        String[] items = cartItems.getItems().split(",");
        int cartsize = (items.length > cartmaxsize) ? (cartmaxsize + 1) : items.length;

        for (int i = 0; i < cartsize; i++) {
            LightweightProductDto lightweightProductDto = productRepository.fetchLightweightProductBySku(items[i]);

            if (lightweightProductDto != null) {
                products.add(lightweightProductDto);
            }
        }

        return products;
    }
}
