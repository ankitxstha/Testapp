package com.example.testapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private Spinner mGenderSpinner;
    private EditText mFirstname, mLastname, mEmail, mTypepassword, mRetypepassword, mCurrentaddress, mPermanentaddress, mPhonenumber, mBirth;
    private String firstname, lastname, email, password1, password2, currentaddress, permanentaddress, phonenumber, birth, picture;
    private int id, gender;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;
    Calendar myCalendar = Calendar.getInstance();

    private int mGender = 0;
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    private Menu action;

    private Bitmap bitmap;
    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFirstname = (EditText) findViewById(R.id.firstname);
        mLastname = (EditText) findViewById(R.id.lastname);
        mEmail = (EditText) findViewById(R.id.email);
        mTypepassword = (EditText) findViewById(R.id.password1);
        mRetypepassword = (EditText) findViewById(R.id.password2);
        mCurrentaddress = (EditText) findViewById(R.id.currentadd);
        mPermanentaddress = (EditText) findViewById(R.id.permanentadd);
        mPhonenumber = (EditText) findViewById(R.id.phonenumber);
        mBirth = (EditText) findViewById(R.id.birth);
        mPicture = findViewById(R.id.picture);

        mGenderSpinner = findViewById(R.id.gender);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);

        mGenderSpinner = findViewById(R.id.gender);
        mBirth = findViewById(R.id.birth);

        mBirth.setFocusableInTouchMode(false);
        mBirth.setFocusable(false);
        mBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Signup.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        setupSpinner();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        email = intent.getStringExtra("email");
        password1 = intent.getStringExtra("type password");
        password2 = intent.getStringExtra("retype password");
        currentaddress = intent.getStringExtra("currentaddress");
        permanentaddress = intent.getStringExtra("permanentaddress");
        phonenumber = intent.getStringExtra("phonenumber");
        birth = intent.getStringExtra("birth");
        picture = intent.getStringExtra("picture");
        gender = intent.getIntExtra("gender", 0);

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + firstname.toString());

            mFirstname.setText(firstname);
            mLastname.setText(lastname);
            mEmail.setText(email);
            mTypepassword.setText(password1);
            mRetypepassword.setText(password2);
            mCurrentaddress.setText(currentaddress);
            mPermanentaddress.setText(permanentaddress);
            mPhonenumber.setText(phonenumber);
            mBirth.setText(birth);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(Signup.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

            switch (gender) {
                case GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }

        } else {
            getSupportActionBar().setTitle("Add a Pet");
        }
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else {
                        mGender = GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mFirstname, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (id == 0) {

                    if (TextUtils.isEmpty(mFirstname.getText().toString()) ||
                            TextUtils.isEmpty(mLastname.getText().toString()) ||
                            TextUtils.isEmpty(mEmail.getText().toString()) ||
                            TextUtils.isEmpty(mTypepassword.getText().toString()) ||
                            TextUtils.isEmpty(mRetypepassword.getText().toString()) ||
                            TextUtils.isEmpty(mCurrentaddress.getText().toString()) ||
                            TextUtils.isEmpty(mPermanentaddress.getText().toString()) ||
                            TextUtils.isEmpty(mPhonenumber.getText().toString())) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {

                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();

                    }

                } else {

                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this);
                dialog.setMessage("Delete this Customer?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id, picture);
                    }
                });
                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };

    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBirth.setText(sdf.format(myCalendar.getTime()));
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String firstname = mFirstname.getText().toString().trim();
        String lastname = mLastname.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mTypepassword.getText().toString();
        String password2 = mRetypepassword.getText().toString().trim();
        String currentaddress = mCurrentaddress.getText().toString().trim();
        String permanentaddress = mPermanentaddress.getText().toString().trim();
        String phonenumber = mPhonenumber.getText().toString().trim();


        int gender = mGender;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<APIResponse> call = apiInterface.insertPet(key, firstname, lastname, email, password, currentaddress, permanentaddress, phonenumber, gender, birth, picture);

        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                progressDialog.dismiss();
                APIResponse result = response.body();
                if (result.getError()) {
                    Toast.makeText(Signup.this, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    Toast.makeText(Signup.this, "Registration success: UUID:" + result.getId(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, t.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

//            @Override
//            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
//
//                progressDialog.dismiss();
//
//                Log.i(Signup.class.getSimpleName(), response.toString());
//
//                String value = response.body().getValue();
//                String message = response.body().getMessage();
//
//                if (value.equals("1")){
//                    finish();
//                } else {
//                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Customer> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(Signup.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String firstname = mFirstname.getText().toString().trim();
        String lastname = mLastname.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mTypepassword.getText().toString();
        String password2 = mRetypepassword.getText().toString().trim();
        String currentaddress = mCurrentaddress.getText().toString().trim();
        String permanentaddress = mPermanentaddress.getText().toString().trim();
        String phonenumber = mPhonenumber.getText().toString().trim();
        int gender = mGender;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Customer> call = apiInterface.updatePet(key, id, firstname, lastname, email, password, currentaddress, permanentaddress, phonenumber, gender, birth, picture);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                progressDialog.dismiss();

                Log.i(Signup.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Customer> call = apiInterface.deletePet(key, id, pic);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                progressDialog.dismiss();

                Log.i(Signup.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }




    void readMode(){

        mFirstname.setFocusableInTouchMode(false);
        mLastname.setFocusableInTouchMode(false);
        mEmail.setFocusableInTouchMode(false);
        mTypepassword.setFocusableInTouchMode(false);
        mRetypepassword.setFocusableInTouchMode(false);
        mCurrentaddress.setFocusableInTouchMode(false);
        mPermanentaddress.setFocusableInTouchMode(false);
        mPhonenumber.setFocusableInTouchMode(false);



        mFirstname.setFocusable(false);
        mLastname.setFocusable(false);
        mEmail.setFocusable(false);
        mTypepassword.setFocusable(false);
        mRetypepassword.setFocusable(false);
        mCurrentaddress.setFocusable(false);
        mPermanentaddress.setFocusable(false);
        mPhonenumber.setFocusable(false);



        mGenderSpinner.setEnabled(false);
        mBirth.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);


    }

    private void editMode(){

        mFirstname.setFocusableInTouchMode(true);
        mLastname.setFocusableInTouchMode(true);
        mEmail.setFocusableInTouchMode(true);
        mTypepassword.setFocusableInTouchMode(true);
        mRetypepassword.setFocusableInTouchMode(true);
        mCurrentaddress.setFocusableInTouchMode(true);
        mPermanentaddress.setFocusableInTouchMode(true);
        mPhonenumber.setFocusableInTouchMode(true);

        mGenderSpinner.setEnabled(true);
        mBirth.setEnabled(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}
