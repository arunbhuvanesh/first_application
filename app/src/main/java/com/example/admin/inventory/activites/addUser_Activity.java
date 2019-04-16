package com.example.admin.inventory.activites;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.customerAdapter;
import com.example.admin.inventory.adapter.vendorAdapter;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addUser_Activity extends AppCompatActivity {
    Button save;
    CircularImageView imageView;
    EditText name, address, area, email, phonenumber, reference, rphone;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private customerAdapter adapter;
    private List<Customers> customersList;
    boolean isnumbervalid;


    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    @BindView(R.id.img_profile)
    ImageView imgProfile;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save = findViewById(R.id.save);
        name = findViewById(R.id.user);
        address = findViewById(R.id.address);
        area = findViewById(R.id.area);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phone);
        reference = findViewById(R.id.reference);
        rphone = findViewById(R.id.Refphone);
        imageView = findViewById(R.id.img_profile);

        ButterKnife.bind(this);

        loadProfileDefault();
        ImagePickerActivity.clearCache(this);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //init();
        //loadCustomers();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(addUser_Activity.this).setTitle("Set Your image").setCancelable(false).setNegativeButton("no", null).show();


            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidUser(name.getText().toString())) {
                    name.setError("Enter charcters only");
                }
            }
        });
        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidArea(area.getText().toString())) {
                    area.setError("Enter charcters only");
                }
            }
        });

        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidNumber(phonenumber.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    phonenumber.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }


            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Enter a valid address");
                }

            }
        });
        reference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidRef(reference.getText().toString())) {
                    reference.setError("Enter charcters only");
                }
            }
        });
        rphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidRNumber(rphone.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    rphone.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString();
                String addr = address.getText().toString();
                String areaa = area.getText().toString();
                String mail = email.getText().toString();
                String phone = phonenumber.getText().toString();
                String ref = reference.getText().toString();
                String refphone = rphone.getText().toString();


                try {
                    if (username.isEmpty() && addr.isEmpty() && areaa.isEmpty() && mail.isEmpty() && phone.isEmpty() && ref.isEmpty() && refphone.isEmpty()) {
                        Toasty.error(addUser_Activity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                      /*  name.setError("Name is required");
                        name.requestFocus();
                    } else if (addr.isEmpty()) {
                        address.setError("Address is required");
                        address.requestFocus();
                    } else if (areaa.isEmpty()) {
                        address.setError("Area is required");
                        address.requestFocus();
                    }
                     *//*   else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                            email.setError("Check your email");
                            email.requestFocus();
                        
                    }*//*
                    else if (phone.isEmpty()) {
                        phonenumber.setError("Phonenumber required");
                        phonenumber.requestFocus();
                    } else if (phone.length() < 10 || phone.length() > 10) {
                        phonenumber.setError("Enter valid Number");
                        phonenumber.requestFocus();
                    } else if (ref.isEmpty()) {
                        reference.setError("its must");
                        reference.requestFocus();
                    } else if (refphone.length() < 10 || refphone.length() > 10) {
                        rphone.setError("Checkout phone number");
                        rphone.requestFocus();*/

                    } else {
                        addcustomer(username, addr, areaa, mail, phone, ref, refphone);
                    }
                    //startActivity(new Intent(addUser_Activity.this, HomeActivity.class));
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });
    }



    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        GlideApp.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {
        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, R.color.hint));
    }

    @OnClick({R.id.img_plus, R.id.img_profile})
    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(addUser_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(addUser_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     */

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     *//*

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(addUser_Activity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
*/


   /* private void init() {
        recyclerView=findViewById(R.id.userrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }*/
    private void addcustomer(String username, String addr, String areaa, String mail, String phone, String ref, String refphone) {
        Call<ResponseBody> call = apiInterface.addCustomers(username, addr, areaa, mail, phone, ref, refphone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Customer added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addUser_Activity.this, show_Userdetails.class));
                    finish();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Customer not added!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isValidUser(String toString) {
        if (toString == null) {
            return false;
        } else {
            String USER_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(USER_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidArea(String toString) {
        if (toString == null) {
            return false;
        } else {
            String AREA_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(AREA_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidRef(String toString) {
        if (toString == null) {
            return false;
        } else {
            String REF_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(REF_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    public final static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            //android Regex to check the email address Validation
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isValidNumber(String toString) {
        if (toString == null) {
            return false;
        } else {
            String PHONE_PATTERN = "[6-9][0-9]{9}";
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidRNumber(String toString) {
        if (toString == null) {
            return false;
        } else {
            String PHONE_PATTERN = "[6-9][0-9]{9}";
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout)
        {
            sharePreference=getApplicationContext().getSharedPreferences(shared_prefer,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharePreference.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(addUser_Activity.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
