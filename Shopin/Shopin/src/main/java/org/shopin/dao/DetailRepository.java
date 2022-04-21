package org.shopin.dao;

import org.shopin.dto.ProductDto;
import org.shopin.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DetailRepository extends JpaRepository<Detail, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT a.sku AS sku, a.discount AS discount, a.image AS image, a.price AS price, "
            + "a.name AS name, a.sizes AS sizes, b.description AS description "
            + "FROM Product a, Detail b WHERE a.live=TRUE AND a.sku=b.sku AND a.sku=?1")
    ProductDto fetchDetailBySku(String sku);

    @Modifying
    @Transactional
    @Query("DELETE FROM Detail p WHERE p.sku=?1")
    int deleteOneDetail(String sku);
}
