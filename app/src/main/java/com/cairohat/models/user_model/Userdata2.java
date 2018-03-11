package com.cairohat.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Userdata2 {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_email")
    @Expose
    private String email ;


    @SerializedName("first_name")
    @Expose
    private String firstname;

    @SerializedName("last_name")
    @Expose
    private String lastname;

    @SerializedName("wiloke_address")
    @Expose
    private String address;

    @SerializedName("wiloke_city")
    @Expose
    private String city;

    @SerializedName("wiloke_zipcode")
    @Expose
    private String postcode;

    @SerializedName("wiloke_country")
    @Expose
    private String country;

    @SerializedName("wiloke_phone")
    @Expose
    private String phone;

    @SerializedName("wp_user_avatar")
    @Expose
    private String image;

    @SerializedName("user_nicename")
    @Expose
    private String nicename;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getpostcode() {
        return postcode;
    }

    public void setpostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
