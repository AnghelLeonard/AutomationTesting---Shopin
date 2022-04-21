package org.shopin.admin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewProduct {

    @NotNull
    @Pattern(regexp = "(\\{\"product\":\\{\"name\":\"[0-9a-zA-Z ]{5,100}\",\"sku\":\"[0-9A-Za-z]{10}\",\"price\":\"([1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}\\.{1}[0-9]{1,2})\",\"categoryid\":\"[0-9]{1,3}\",\"mcat\":\"(b|f|c)\",\"scat\":\"(im|in|ac)\",\"discount\":\"[0-9]{1,2}\",\"firstpage\":\"(true|false)\",\"live\":\"(true|false)\",\"color\":\"[a-z]{1}\",\"sizes\":\"[/_A-Za-z0-9\\-]{3,500}\",\"description\":\"(@Descriere produs@ )((#)[a-zA-Z0-9%;,: ]{10,800}){1}((#)[a-zA-Z0-9%;,: ]{10,800}){1}((#)[a-zA-Z0-9%;,: ]{10,800}){1}((#)[a-zA-Z0-9%;,: ]{10,800}){0,1}((#)[a-zA-Z0-9%;,: ]{10,800}){0,1}((#)[a-zA-Z0-9%;,: ]{10,800}){0,1}(@Compozitie@ )((#)[a-zA-Z0-9%;,: ]{10,800}){1}((#)[a-zA-Z0-9%;,: ]{10,800}){0,1}\"\\}\\})")
    private String product = "";

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
