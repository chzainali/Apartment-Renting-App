package com.example.appartmentrentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.HelperClass;
import com.example.appartmentrentingapp.model.UsersModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText emailEt, passET;
    AppCompatButton btnLogin;
    LinearLayout llRegister;
    String email, password;
    DatabaseHelper databaseHelper;
    Boolean checkDetails = false;
    List<UsersModel> list = new ArrayList<>();
    String adminEmail = "admin@gmail.com";
    String adminPassword = "admin@123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.emailEt);
        passET = findViewById(R.id.passET);
        btnLogin = findViewById(R.id.btnLogin);
        llRegister = findViewById(R.id.llRegister);
        databaseHelper = new DatabaseHelper(this);

        if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        llRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user input
                if (isValidated()) {
                    // Check if the user is the admin
                    if (email.contentEquals(adminEmail) && password.contentEquals(adminPassword)) {
                        // Start the AdminMainActivity for the admin user
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        finish();
                    }
                    else {
                        // Retrieve all users from the database
                        list = databaseHelper.getAllUsers();
                        for (UsersModel users : list) {
                            if (email.equals(users.getEmail()) && password.equals(users.getPassword())) {
                                checkDetails = true;
                                showMessage("Successfully Login");
                                HelperClass.users = users;
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                break;
                            }
                        }
                        if (!checkDetails) {
                            showMessage("Wrong Credentials...\nPlease check email or password");
                        }
                    }
                }
            }
        });

    }

    private Boolean isValidated() {
        email = emailEt.getText().toString().trim();
        password = passET.getText().toString().trim();

        if (email.isEmpty()) {
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
            return false;
        }
        if (password.isEmpty()) {
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}