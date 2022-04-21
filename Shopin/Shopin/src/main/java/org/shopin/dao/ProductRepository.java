package org.shopin.dao;

import java.util.List;
import org.shopin.dto.LightweightProductDto;
import org.shopin.dto.SuperLightweightProductDto;
import org.shopin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(p) FROM Product p WHERE p.live = TRUE")
    long fetchAllCount();

    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(p) FROM Product p WHERE p.live = TRUE AND p.image LIKE ?1")
    long fetchCategoryAndRangeCount(String catorran);

    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(p) FROM Product p WHERE p.live = TRUE AND p.category_id=?1")
    long fetchSubcategoryCount(long id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT DISTINCT p.color FROM Product p WHERE p.live = TRUE AND p.category_id=?1")
    List<String> fetchAllColorsByCategory(long id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT MAX(p.price) FROM Product p WHERE p.live = TRUE")
    Float fetchAllPrice();

    @Transactional(readOnly = true)
    @Query(value = "SELECT MAX(p.price) FROM Product p WHERE p.live = TRUE AND p.category_id=?1")
    Float fetchAllPricesByCategory(long id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT p.sku AS sku, p.discount AS discount, p.image AS image, p.price AS price, "
            + "p.sizes AS sizes, p.name AS name, p.live AS live FROM Product p WHERE p.sku=?1")
    LightweightProductDto fetchLightweightProductBySku(String sku);

    @Transactional(readOnly = true)
    @Query(value = "SELECT p.discount AS discount, p.price AS price, "
            + "p.sizes AS sizes, p.live AS live FROM Product p WHERE p.sku=?1")
    SuperLightweightProductDto fetchSuperLightweightProductBySku(String sku);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.sku=?1")
    int deleteOneProduct(String sku);
}
