package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetails extends AppCompatActivity {
    ImageView imageView;
    TextView name,description, price, id;
    int  productID, productTotal, productPrice ;
    int customerID = 16;
    int productQuantity = 1;
    String   productname, productdescription ;
    String image;
    Button AddCartBtn;

    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        AddCartBtn = findViewById(R.id.AddCartBtn);
        AddCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });


        //getting string and showing it in activity_product_details
        imageView=findViewById(R.id.image);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        id=findViewById(R.id.id);


        //here productid is string but int in database( can use getIntExtra)
        productID=getIntent().getIntExtra("productID", 420);
        productname=getIntent().getStringExtra("name");
        productdescription=getIntent().getStringExtra("description");
        image=getIntent().getStringExtra("image_url");
        productPrice=getIntent().getIntExtra("price",420);

        productTotal = productQuantity * productPrice;

        name.setText(productname);
        description.setText(productdescription);
        price.setText("" + productPrice);
        id.setText(""+productID);



        Picasso.get()
                .load(image)
                .into(imageView);
    }

    private void addToCart() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving to your cart");
        progressDialog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //for the moment i have initialized the value of customerID = 1
        Call<APIResponse> call = apiInterface.insertToCart(customerID, productID,productname,productPrice,productQuantity,productTotal);
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                progressDialog.dismiss();
                APIResponse result = response.body();

                if (result.getError()) {
                    Toast.makeText(ProductDetails.this, result.getErrorMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    finish();
                    Toast.makeText(ProductDetails.this, "insert into cart sucessfull" + result.getId(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProductDetails.this, t.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
