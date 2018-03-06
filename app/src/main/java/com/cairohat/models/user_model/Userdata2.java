package com.cairohat.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 2/26/2018.
 */

public class Userdata2 {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_email")
    @Expose
    private String email ;
    @SerializedName("user_nicename")
    @Expose
    private String nicename;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }
}
