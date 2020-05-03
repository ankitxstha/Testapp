package com.example.testapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("CartID")
    @Expose
    private int CartID;


    @SerializedName("customerID")
    @Expose
    private int customerID ;


    @SerializedName("productID")
    @Expose
    private int productID ;


    @SerializedName("productName")
    @Expose
    private String productName ;


    @SerializedName("productQuantity")
    @Expose
    private int productQuantity ;


    @SerializedName("productPrice")
    @Expose
    private int productPrice ;


    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    @SerializedName("productTotal")
    @Expose
    private int productTotal ;
}
