package org.shopin.dto;

public class FilterProductDto {

    private String sku;
    private long category_id;
    private int discount;
    private String image;
    private float price;
    private String name;

    public FilterProductDto(String sku, long category_id, int discount, String image, float price, String name) {
        this.sku = sku;
        this.category_id = category_id;
        this.discount = discount;
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
