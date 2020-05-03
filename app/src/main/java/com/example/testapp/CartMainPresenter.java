package com.example.testapp;

public class CartMainPresenter {

    private CartMainView view;

    public CartMainPresenter(CartMainView view) {
        this.view = view;
    }

    void getData(){
        view.showLoading();
        //request to server
    }
}
