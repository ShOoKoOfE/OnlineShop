package project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Factor {
    @SerializedName("factor_id")
    @Expose
    private int factor_id;
    @SerializedName("factor_number")
    @Expose
    private String factor_number;
    @SerializedName("factor_date")
    @Expose
    private String factor_date;
    @SerializedName("factor_price")
    @Expose
    private long factor_price;

    public int getFactorId() {
        return factor_id;
    }

    public void setFactorId(int factor_id) {
        this.factor_id = factor_id;
    }

    public String getFactorNumber() {
        return factor_number;
    }

    public void setFactorNumber(String factor_number) {
        this.factor_number = factor_number;
    }

    public String getFactorDate() {
        return factor_date;
    }

    public void setFactorDate(String factor_date) {
        this.factor_date = factor_date;
    }

    public long getFactorPrice() {
        return factor_price;
    }

    public void setFactorPrice(long factor_price) {
        this.factor_price = factor_price;
    }
}
