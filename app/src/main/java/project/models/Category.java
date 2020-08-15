package project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("category_id")
    @Expose
    private int category_id;
    @SerializedName("category_type")
    @Expose
    private String category_type;
    @SerializedName("category_code")
    @Expose
    private String category_code;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("category_imageUrl")
    @Expose
    private String category_imageUrl;
    @SerializedName("category_main")
    @Expose
    private byte category_main;

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryType() {
        return category_type;
    }

    public void setCategoryType(String category_type) {
        this.category_type = category_type;
    }

    public String getCategoryCode() {
        return category_code;
    }

    public void setCategoryCode(String category_code) {
        this.category_code = category_code;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public String getCategoryImageUrl() {
        return category_imageUrl;
    }

    public void setCategoryImageUrl(String category_imageUrl) {
        this.category_imageUrl = category_imageUrl;
    }

    public byte getCategoryMain() {
        return category_main;
    }

    public void setCategoryMain(byte category_main) {
        this.category_main = category_main;
    }

}
