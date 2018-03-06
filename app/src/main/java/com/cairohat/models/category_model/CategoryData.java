
package com.cairohat.models.category_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class CategoryData {

    private String id;
    private String name;
     private String parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    //    @SerializedName("success")
//    @Expose
//    private String success;
//    @SerializedName("")
//    @Expose
//    private CategoryDetails data = null;
//    @SerializedName("message")
//    @Expose
//    private String message;
//    @SerializedName("categories")
//    @Expose
//    private Integer categories;

    
//    public String getSuccess() {
//        return success;
//    }
//
//    public void setSuccess(String success) {
//        this.success = success;
//    }

//    public CategoryDetails getData() {
//        return data;
//    }
//
//    public void setData(CategoryDetails data) {
//        this.data = data;
//    }

//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Integer getCategories() {
//        return categories;
//    }
//
//    public void setCategories(Integer categories) {
//        this.categories = categories;
//    }
//
}
