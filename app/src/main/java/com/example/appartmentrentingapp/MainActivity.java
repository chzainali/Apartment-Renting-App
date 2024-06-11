package com.example.appartmentrentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appartmentrentingapp.model.HelperClass;

public class MainActivity extends AppCompatActivity {
    TextView tvWelcome;
    AppCompatButton btnAll, btnMyBookings, btnLogout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAll = findViewById(R.id.btnAll);
        btnMyBookings = findViewById(R.id.btnMyBookings);
        btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("Welcome, "+ HelperClass.users.getUserName());

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApartmentsActivity.class);
                intent.putExtra("from", "user");
                startActivity(intent);
            }
        });

        btnMyBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApartmentsActivity.class);
                intent.putExtra("from", "bookings");
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });

    }
}