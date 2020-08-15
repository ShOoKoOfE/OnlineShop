package project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_id")
    @Expose
    private int product_id;
    @SerializedName("product_code")
    @Expose
    private String product_code;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_price")
    @Expose
    private long product_price;
    @SerializedName("product_imageUrl")
    @Expose
    private String product_imageUrl;
    @SerializedName("product_factory")
    @Expose
    private String product_factory;
    @SerializedName("product_rate")
    @Expose
    private float product_rate;
    @SerializedName("product_count")
    @Expose
    private int product_count;
    @SerializedName("basket_product_count")
    @Expose
    private int basket_product_count;

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public String getProductCode() {
        return product_code;
    }

    public void setProductCode(String product_code) {
        this.product_code = product_code;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public long getProductPrice() {
        return product_price;
    }

    public void setProductPrice(long product_price) {
        this.product_price = product_price;
    }

    public String getProductImageUrl() {
        return product_imageUrl;
    }

    public void setProductImageUrl(String product_imageUrl) {
        this.product_imageUrl = product_imageUrl;
    }

    public String getProductFactory() {
        return product_factory;
    }

    public void setProductFactory(String product_factory) {
        this.product_factory = product_factory;
    }

    public float getProductRate() {
        return product_rate;
    }

    public void setProductRate(float product_rate) {
        this.product_rate = product_rate;
    }

    public int getProductCount() {
        return product_count;
    }

    public void setProductCount(int product_count) {
        this.product_count = product_count;
    }

    public int getBasketProductCount() {
        return basket_product_count;
    }

    public void setBasketProductCount(int basket_product_count) {
        this.basket_product_count = basket_product_count;
    }

    public long ProductPrice() {
        return getBasketProductCount() * getProductPrice();
    }
}
