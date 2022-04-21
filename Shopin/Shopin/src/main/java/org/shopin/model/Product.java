package org.shopin.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.ehcache.sizeof.annotations.IgnoreSizeOf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.shopin.dto.FilterProductDto;

@SqlResultSetMapping(name = "FetchSearchedProductsMapping",
        classes = {
            @ConstructorResult(targetClass = FilterProductDto.class,
                    columns = {
                        @ColumnResult(name = "sku", type = String.class)
                        , 
                        @ColumnResult(name = "category_id", type = Long.class)
                        ,
                        @ColumnResult(name = "discount", type = Integer.class)
                        ,
                        @ColumnResult(name = "image", type = String.class)
                        ,
                        @ColumnResult(name = "price", type = Float.class)
                        ,
                        @ColumnResult(name = "name", type = String.class)
                    }
            )}
)
@Entity
@Immutable
@IgnoreSizeOf
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private Long category_id;

    @Column(nullable = false)
    private int discount;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(length = 10, nullable = false, unique = true)
    private String sku;

    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private float price;

    @NotNull
    @Column(length = 6, nullable = false)
    private String image;

    @NotNull
    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private boolean live;

    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private boolean firstpage;

    @NotNull
    @Column(length = 1, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String color;

    @NotNull
    @Column(length = 500, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String sizes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isFirstpage() {
        return firstpage;
    }

    public void setFirstpage(boolean firstpage) {
        this.firstpage = firstpage;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
