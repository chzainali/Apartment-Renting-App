package com.example.appartmentrentingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.ApartmentModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddApartmentActivity extends AppCompatActivity {
    ImageView ivBack, ivImage;
    TextInputEditText addressEt, priceEt, ownerNameEt, ownerPhoneEt, descriptionEt;
    AppCompatButton btnAdd;
    String address, price, ownerName, ownerPhone, description, imageUri = "";
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        ivBack = findViewById(R.id.ivBack);
        ivImage = findViewById(R.id.ivImage);
        addressEt = findViewById(R.id.addressEt);
        priceEt = findViewById(R.id.priceEt);
        ownerNameEt = findViewById(R.id.ownerNameEt);
        ownerPhoneEt = findViewById(R.id.ownerPhoneEt);
        descriptionEt = findViewById(R.id.descriptionEt);
        btnAdd = findViewById(R.id.btnAdd);

        databaseHelper = new DatabaseHelper(this);
        ivBack.setOnClickListener(v -> finish());

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()) {
                    ApartmentModel apartmentModel = new ApartmentModel(imageUri, address, price, ownerName, ownerPhone, description, "Available", "", "", "");
                    databaseHelper.addApartment(apartmentModel);
                    showMessage("Added Successfully");
                    finish();
                }
            }
        });
    }

    private Boolean isValidated() {
        address = addressEt.getText().toString().trim();
        price = priceEt.getText().toString().trim();
        ownerName = ownerNameEt.getText().toString().trim();
        ownerPhone = ownerPhoneEt.getText().toString().trim();
        description = descriptionEt.getText().toString().trim();

        if (imageUri.isEmpty()) {
            showMessage("Please pick an image");
            return false;
        }

        if (address.isEmpty()) {
            showMessage("Please enter address");
            return false;
        }

        if (price.isEmpty()) {
            showMessage("Please enter price");
            return false;
        }

        if (ownerName.isEmpty()) {
            showMessage("Please enter owner name");
            return false;
        }

        if (ownerPhone.isEmpty()) {
            showMessage("Please enter owner phone");
            return false;
        }

        if (description.isEmpty()) {
            showMessage("Please enter description");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            // Set the selected image URI to the ImageView
            imageUri = data.getData().toString();
            ivImage.setImageURI(data.getData());
        }
    }

}