package org.shopin.admin;

import org.shopin.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<Detail, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT a.category_id AS category_id, a.sku AS sku, a.discount AS discount, a.image AS image, a.price AS price, "
            + "a.name AS name, a.sizes AS sizes, a.color AS color, a.firstpage AS firstpage, "
            + "a.live AS live, b.description AS description "
            + "FROM Product a, Detail b WHERE a.sku=b.sku AND a.sku=?1")
    HeavyProductDto fetchHeavyBySku(String sku);
}
