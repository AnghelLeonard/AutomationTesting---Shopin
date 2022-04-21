package org.shopin.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CartItems {

    @NotNull
    @Pattern(regexp = "([A-Za-z0-9]{10},)*[A-Za-z0-9]{10}")
    private String items = "";

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
