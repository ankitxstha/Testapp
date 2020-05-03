package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.testapp.KEYS.KEY_SHARED_PREF;

public class HomeActivity extends AppCompatActivity implements MainView{

    private ApiInterface apiInterface;
    RelativeLayout relative;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    MainPresenter presenter;
    HomeAdapter homeAdapter;
    HomeAdapter.ItemClickListener itemClickListener;
    List<Product> product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SHARED_PREF,MODE_PRIVATE);
        String name = sharedPreferences.getString("firstname","");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.clear();

        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new MainPresenter(this);
        presenter.getData();

        swipeRefresh.setOnRefreshListener(
                () -> presenter.getData()
        );


        //when item clicked
        itemClickListener = ((view, position) -> {
            String title = product.get(position).getName();
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

        });
    }


    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Product> products) {
        homeAdapter = new HomeAdapter(this, products, itemClickListener);
        homeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(homeAdapter);

        product = products;


    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    private void loadData(){
//
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<List<Product>> call = apiInterface.getProducts();
//        call.enqueue(new Callback<List<Product>>(){
//
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                product = response.body();
//
//                homeAdapter = new HomeAdapter(HomeActivity.this, product);
//                recyclerView.setAdapter(homeAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });
//
//    }
}
