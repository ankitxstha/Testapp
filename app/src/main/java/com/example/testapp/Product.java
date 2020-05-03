package com.example.testapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("model_number")
    @Expose
    private String model_number;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("img_url")
    @Expose
    private String img_url;
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;


    public Product(int id, String name, String model_number, int price, String category, String description, String img_url, Boolean success, String message) {
        this.id = id;
        this.name = name;
        this.model_number = model_number;
        this.price = price;
        this.category = category;
        this.description = description;
        this.img_url = img_url;
        this.success = success;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
