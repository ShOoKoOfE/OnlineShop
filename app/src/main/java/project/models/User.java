package project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("users_id")
    @Expose
    private int users_id;
    @SerializedName("users_username")
    @Expose
    private String users_username;
    @SerializedName("users_password")
    @Expose
    private String users_password;
    @SerializedName("users_mobile")
    @Expose
    private String users_mobile;
    @SerializedName("users_email")
    @Expose
    private String users_email;

    public int getUserId() {
        return users_id;
    }

    public void setUserId(int users_id) {
        this.users_id = users_id;
    }

    public String getUserUsername() {
        return users_username;
    }

    public void setUserUsername(String users_username) {
        this.users_username = users_username;
    }

    public String getUserPassword() {
        return users_password;
    }

    public void setUserPassword(String users_password) {
        this.users_password = users_password;
    }

    public String getUserMobile() {
        return users_mobile;
    }

    public void setUserMobile(String users_mobile) {
        this.users_mobile = users_mobile;
    }

    public String getUserEmail() {
        return users_email;
    }

    public void setUserEmail(String users_email) {
        this.users_email = users_email;
    }
}
