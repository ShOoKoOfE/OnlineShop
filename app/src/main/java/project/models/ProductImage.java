package project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {
    @SerializedName("image_id")
    @Expose
    private int image_id;
    @SerializedName("product_imageUrl")
    @Expose
    private String product_imageUrl;
    @SerializedName("image_order")
    @Expose
    private int image_order;

    public int getImageId() {
        return image_id;
    }

    public void setImageId(int image_id) {
        this.image_id = image_id;
    }

    public String getProductImageUrl() {
        return product_imageUrl;
    }

    public void setProductImageUrl(String product_imageUrl) {
        this.product_imageUrl = product_imageUrl;
    }

    public int getImageOrder() {
        return image_order;
    }

    public void setImageOrder(int image_order) {
        this.image_order = image_order;
    }
}
