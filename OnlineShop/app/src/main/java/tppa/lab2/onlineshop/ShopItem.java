package tppa.lab2.onlineshop;

import java.io.Serializable;

public class ShopItem implements Serializable {

    private String productName;
    private Float productPrice;
    private String productDescription;
    private Integer productImageResource;

    public ShopItem(String productName, Float productPrice, String productDescription, Integer productImageResource) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImageResource = productImageResource;
    }

    public String getProductName() {
        return productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Integer getProductImageResource() {
        return productImageResource;
    }
}
