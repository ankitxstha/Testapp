package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.testapp.KEYS.KEY_EMAIL;
import static com.example.testapp.KEYS.KEY_NAME;
import static com.example.testapp.KEYS.KEY_SHARED_PREF;


public class MainActivity extends AppCompatActivity {

    private EditText email_et, password_et;
    public ApiInterface apiInterface;
    public Button login_btn, knowMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        directLogin();

        Button fab = findViewById(R.id.signupButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });

        knowMore = findViewById(R.id.knowMore);
        knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class ));
            }
        });

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        email_et = findViewById(R.id.emailEditText);
        password_et = findViewById(R.id.passEditText);

        login_btn = findViewById(R.id.signinButton);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt = email_et.getText().toString().toLowerCase().trim();
                String passwordTxt = password_et.getText().toString();
                authUser(emailTxt,passwordTxt);
            }
        });

    }

    private void authUser(String emailTxt, String passwordTxt) {
        apiInterface.userLogin(emailTxt,passwordTxt).enqueue(new Callback<ArrayList<APIResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<APIResponse>> call, Response<ArrayList<APIResponse>> response) {
                ArrayList<APIResponse> user = response.body();
                SharedPreferences sharedPreferences = getSharedPreferences(KEY_SHARED_PREF,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",user.get(0).getEmail());
                editor.putString("firstname",user.get(0).getFirstname());
                editor.putString("lastname",user.get(0).getLastname());
                editor.putString("id",user.get(0).getId());
                editor.apply();
                Toast.makeText(MainActivity.this, user.get(0).getEmail(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ArrayList<APIResponse>> call, Throwable t) {

            }
        });
    }

    public void directLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SHARED_PREF,MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");

        if(!id.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            //     Toast.makeText(getActivity(), "WELCOME,"+uuid, Toast.LENGTH_SHORT).show();
        }
    }
}
