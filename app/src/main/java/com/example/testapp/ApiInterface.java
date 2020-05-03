package com.example.testapp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("get_customer.php")
    Call<List<Customer>> getPets();

    @FormUrlEncoded
    @POST("add_customer.php")
    Call<APIResponse> insertPet(
            @Field("key") String key,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("currentaddress") String currentaddress,
            @Field("permanentaddress") String permanentaddress,
            @Field("phonenumber") String phonenumber,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("userLogin.php")
    Call <ArrayList<APIResponse>> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_customer.php")
    Call<Customer> updatePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("currentaddress") String currentaddress,
            @Field("permanentaddress") String permanentaddress,
            @Field("phonenumber") String phonenumber,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_customer.php")
    Call<Customer> deletePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_love.php")
    Call<Customer> updateLove(
            @Field("key") String key,
            @Field("id") int id,
            @Field("love") boolean love);

//    @GET("retrieveProducts.php")
//    Call<List<Product>> getProducts();

    @GET("retrieveProductss.php")
    Call<List<Product>> getProducts();

    @GET("retrieveCarts.php")
    Call<List<Cart>> getCarts();

    @FormUrlEncoded
    @POST("insertToCart.php")
    Call<APIResponse> insertToCart(
            @Field("customerID") int customerID,
            @Field("productID") int productID,
            @Field("productName") String productName,
            @Field("productPrice") int productPrice,
            @Field("productQuantity") int productQuantity,
            @Field("productTotal") int productTotal

            );
}
