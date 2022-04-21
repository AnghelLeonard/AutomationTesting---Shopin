package org.shopin.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category implements Serializable {

    public static final long serialVersionUID = 424L;

    @Id
    @NotNull
    private Long id;

    @Column(length = 1, nullable = false)
    private String main;

    @Column(length = 2, nullable = false)
    private String type;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 1, nullable = false)
    private String sizeid;

    @Column(length = 100, nullable = true)
    private String colors;

    @Column(nullable = true)
    private float maxprice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }

    public String getSizeid() {
        return sizeid;
    }

    public void setSizeid(String sizeid) {
        this.sizeid = sizeid;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
