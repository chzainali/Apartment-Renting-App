package com.example.appartmentrentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appartmentrentingapp.adapter.ApartmentAdapter;
import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.ApartmentModel;
import com.example.appartmentrentingapp.model.HelperClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApartmentsActivity extends AppCompatActivity {
    String from = "";
    ImageView ivBack;
    TextView tvLabel, noDataFound;
    RecyclerView rvApartments;
    DatabaseHelper databaseHelper;
    ApartmentAdapter apartmentAdapter;
    List<ApartmentModel> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments);

        ivBack = findViewById(R.id.ivBack);
        tvLabel = findViewById(R.id.tvLabel);
        noDataFound = findViewById(R.id.noDataFound);
        rvApartments = findViewById(R.id.rvApartments);
        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() != null){
            from = getIntent().getStringExtra("from");
            if (Objects.equals(from, "user")){
                tvLabel.setText("All Apartments");
            }else{
                tvLabel.setText("My Bookings");
            }
        }

        ivBack.setOnClickListener(view -> finish());

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        if (Objects.equals(from, "user")) {
            list.addAll(databaseHelper.getAllApartments());
        }else{
            list.addAll(databaseHelper.getApartmentsByUserId(String.valueOf(HelperClass.users.getId())));
        }

        if (list.size() > 0) {
            noDataFound.setVisibility(View.GONE);
            rvApartments.setVisibility(View.VISIBLE);
            rvApartments.setLayoutManager(new LinearLayoutManager(this));
            apartmentAdapter = new ApartmentAdapter(list, this, from);
            rvApartments.setAdapter(apartmentAdapter);
            apartmentAdapter.notifyDataSetChanged();
        } else {
            noDataFound.setVisibility(View.VISIBLE);
            rvApartments.setVisibility(View.GONE);
        }
    }

}