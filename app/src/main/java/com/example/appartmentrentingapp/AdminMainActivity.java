package com.example.appartmentrentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appartmentrentingapp.adapter.ApartmentAdapter;
import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.ApartmentModel;

import java.util.ArrayList;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity {
    ImageView ivLogout;
    TextView noDataFound;
    RelativeLayout rlAdd;
    RecyclerView rvApartments;
    DatabaseHelper databaseHelper;
    ApartmentAdapter apartmentAdapter;
    List<ApartmentModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        ivLogout = findViewById(R.id.ivLogout);
        noDataFound = findViewById(R.id.noDataFound);
        rlAdd = findViewById(R.id.rlAdd);
        rvApartments = findViewById(R.id.rvApartments);
        databaseHelper = new DatabaseHelper(this);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });

        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AddApartmentActivity.class));
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        list.addAll(databaseHelper.getAllApartments());

        if (list.size() > 0) {
            noDataFound.setVisibility(View.GONE);
            rvApartments.setVisibility(View.VISIBLE);
            rvApartments.setLayoutManager(new LinearLayoutManager(this));
            apartmentAdapter = new ApartmentAdapter(list, this, "admin");
            rvApartments.setAdapter(apartmentAdapter);
            apartmentAdapter.notifyDataSetChanged();
        } else {
            noDataFound.setVisibility(View.VISIBLE);
            rvApartments.setVisibility(View.GONE);
        }
    }

}