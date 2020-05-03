package com.example.testapp;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Product> products);
    void onErrorLoading(String message);
}
