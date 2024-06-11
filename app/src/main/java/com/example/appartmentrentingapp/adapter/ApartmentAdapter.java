package com.example.appartmentrentingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.appartmentrentingapp.ApartmentDetailsActivity;
import com.example.appartmentrentingapp.R;
import com.example.appartmentrentingapp.model.ApartmentModel;

import java.util.List;
import java.util.Objects;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.ViewHolder> {
    List<ApartmentModel> list;
    Context context;
    String checkFrom;

    public ApartmentAdapter(List<ApartmentModel> list, Context context, String checkFrom) {
        this.list = list;
        this.context = context;
        this.checkFrom = checkFrom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_apartment, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ApartmentModel apartmentModel = list.get(position);
        holder.tvStatus.setText(apartmentModel.getStatus());
        if (Objects.equals(apartmentModel.getStatus(), "Booked")){
            holder.tvStatus.setTextColor(context.getColor(R.color.booked));
        }else{
            holder.tvStatus.setTextColor(context.getColor(R.color.available));
        }
        holder.tvAddress.setText(apartmentModel.getLocation());
        holder.tvPrice.setText(apartmentModel.getPrice()+" SAR");
        Glide.with(holder.ivImage)
                .asBitmap()
                .load(Uri.parse(apartmentModel.getImage()))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Now you have the Bitmap, you can set it as the image resource.

                        holder.ivImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // This is called when the Drawable is cleared, for example, when the view is recycled.
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApartmentDetailsActivity.class);
                intent.putExtra("from", checkFrom);
                intent.putExtra("data", apartmentModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrice, tvAddress, tvStatus;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
