package org.shopin.dao;

import java.util.List;
import java.util.Optional;
import org.shopin.dto.CategoryDto;
import org.shopin.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT p.id AS id, p.main AS main, p.type AS type, p.category AS category, "
            + "p.colors AS colors, p.sizeid as sizeid, p.maxprice as maxprice FROM Category AS p")
    List<CategoryDto> fetchAllCategories();

    @Transactional(readOnly = true)
    @Query(value = "SELECT p.id FROM Category AS p")
    long[] fetchAllCategoriesIds();

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.colors = ?1 WHERE p.id = ?2")
    int insertCategoryColors(String colors, long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.maxprice = ?1 WHERE p.id = ?2")
    int insertCategoryPrices(Float price, long id);

    // admin queries
    @Transactional(readOnly = true)
    @Query(value = "SELECT p.colors FROM Category AS p WHERE p.id=?1")
    Optional<String> fetchCategoryColors(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.colors = ?1 WHERE p.id = ?2")
    int updateCategoryColors(String color, long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.maxprice = ?1 WHERE p.id = ?2 AND (p.maxprice < ?1 OR p.maxprice IS NULL)")
    int updateCategoryPrice(float price, long id);
}
