package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    RelativeLayout relativeCart;
    RecyclerView recyclerViewCart;
    SwipeRefreshLayout swipeRefreshCart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        swipeRefreshCart = findViewById(R.id.swipe_refresh_cart);
        recyclerViewCart = findViewById(R.id.recycler_view_cart);


    }
}
