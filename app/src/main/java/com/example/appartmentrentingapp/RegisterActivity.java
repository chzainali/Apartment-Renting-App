package com.example.appartmentrentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.UsersModel;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    ImageView ivBack;
    TextInputEditText nameEt, emailEt, phoneEt, passET;
    AppCompatButton btnRegister;
    LinearLayout llBottom;
    String name, email, phone, password;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        passET = findViewById(R.id.passET);
        btnRegister = findViewById(R.id.btnRegister);
        llBottom = findViewById(R.id.llBottom);
        ivBack = findViewById(R.id.ivBack);

        databaseHelper = new DatabaseHelper(this);
        ivBack.setOnClickListener(v -> finish());
        llBottom.setOnClickListener(v -> finish());

        // Set a click listener for the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user input and register if valid
                if (isValidated()){
                    UsersModel users = new UsersModel(name, email, phone, password);
                    databaseHelper.register(users);
                    showMessage("Successfully Registered");
                    finish();
                }
            }
        });

    }

    // Validate user input for registration
    private Boolean isValidated(){
        name = nameEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();
        password = passET.getText().toString().trim();

        if (name.isEmpty()){
            showMessage("Please enter name");
            return false;
        }
        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
            return false;
        }
        if (phone.isEmpty() || phone.length()<10){
            showMessage("Please enter correct phone number");
            return false;
        }
        if (password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    // Show a toast message
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}