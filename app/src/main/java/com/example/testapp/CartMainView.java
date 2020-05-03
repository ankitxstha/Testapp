package com.example.testapp;

import java.util.List;

public interface CartMainView {

    void showLoading();
    void hideLoading();
    void onGetResult(List<Cart> carts);
    void onErrorLoading(String message);
}
