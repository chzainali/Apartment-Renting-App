package com.example.appartmentrentingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.appartmentrentingapp.database.DatabaseHelper;
import com.example.appartmentrentingapp.model.ApartmentModel;
import com.example.appartmentrentingapp.model.HelperClass;
import com.example.appartmentrentingapp.model.UsersModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ApartmentDetailsActivity extends AppCompatActivity {
    ImageView ivBack, ivImage;
    TextView tvLabel, tvStatus;
    TextInputEditText addressEt, priceEt, ownerNameEt, ownerPhoneEt, descriptionEt, nameEt, emailEt, phoneEt, yearsEt, dateEt, expiryDateEt;
    Button btnBook;
    LinearLayoutCompat llBookingDetails;
    DatabaseHelper databaseHelper;
    String from = "";
    ApartmentModel apartmentModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        ivBack = findViewById(R.id.ivBack);
        ivImage = findViewById(R.id.ivImage);
        tvLabel = findViewById(R.id.tvLabel);
        tvStatus = findViewById(R.id.tvStatus);
        addressEt = findViewById(R.id.addressEt);
        priceEt = findViewById(R.id.priceEt);
        ownerNameEt = findViewById(R.id.ownerNameEt);
        ownerPhoneEt = findViewById(R.id.ownerPhoneEt);
        descriptionEt = findViewById(R.id.descriptionEt);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        yearsEt = findViewById(R.id.yearsEt);
        dateEt = findViewById(R.id.dateEt);
        btnBook = findViewById(R.id.btnBook);
        llBookingDetails = findViewById(R.id.llBookingDetails);
        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() != null) {
            from = getIntent().getStringExtra("from");
            apartmentModel = (ApartmentModel) getIntent().getSerializableExtra("data");
            if (Objects.equals(from, "bookings")) {
                btnBook.setVisibility(View.GONE);
                if (Objects.equals(apartmentModel.getStatus(), "Booked")) {
                    llBookingDetails.setVisibility(View.VISIBLE);
                }
                llBookingDetails.setVisibility(View.VISIBLE);
            } else if (Objects.equals(from, "admin")) {
                if (Objects.equals(apartmentModel.getStatus(), "Booked")) {
                    llBookingDetails.setVisibility(View.VISIBLE);
                    btnBook.setText("Remove Booking");
                } else {
                    btnBook.setText("Delete");
                }
            }else{
                if (Objects.equals(apartmentModel.getStatus(), "Booked")) {
                    btnBook.setVisibility(View.GONE);
                }else{
                    btnBook.setVisibility(View.VISIBLE);
                }
                yearsEt.setVisibility(View.GONE);
            }
        }
        if (Objects.equals(apartmentModel.getStatus(), "Booked")){
            tvStatus.setTextColor(getColor(R.color.booked));
        }else{
            tvStatus.setTextColor(getColor(R.color.available));
        }
        tvStatus.setText(apartmentModel.getStatus());
        ivBack.setOnClickListener(view -> finish());

        if (apartmentModel != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(apartmentModel.getImage()))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // Now you have the Bitmap, you can set it as the image resource.
                            ivImage.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // This is called when the Drawable is cleared, for example, when the view is recycled.
                        }
                    });
            addressEt.setText(apartmentModel.getLocation());
            priceEt.setText(apartmentModel.getPrice() + " SAR");
            ownerNameEt.setText(apartmentModel.getOwnerName());
            ownerPhoneEt.setText(apartmentModel.getOwnerPhone());
            descriptionEt.setText(apartmentModel.getDescription());
            if (Objects.equals(apartmentModel.getStatus(), "Booked")) {
                UsersModel model = databaseHelper.getUserById(apartmentModel.getId());
                nameEt.setText(model.getUserName());
                emailEt.setText(model.getEmail());
                phoneEt.setText(model.getPhone());
                yearsEt.setText(apartmentModel.getYears());
                dateEt.setText(apartmentModel.getBookingDate());
            }
        }

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(from, "user")) {
                    showBookingDialog();
                } else if (Objects.equals(from, "admin")) {
                    if (Objects.equals(apartmentModel.getStatus(), "Booked")) {
                        apartmentModel.setStatus("Available");
                        apartmentModel.setUserId("");
                        apartmentModel.setYears("");
                        apartmentModel.setBookingDate("");
                        databaseHelper.updateApartment(apartmentModel);
                        llBookingDetails.setVisibility(View.VISIBLE);
                        btnBook.setText("Delete");
                        tvStatus.setText("Available");
                        tvStatus.setTextColor(getColor(R.color.available));
                        Toast.makeText(ApartmentDetailsActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseHelper.deleteApartment(apartmentModel.getId());
                        Toast.makeText(ApartmentDetailsActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    private void showBookingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.item_booking_dialog);

        TextInputEditText yearsEt = dialog.findViewById(R.id.yearsEt);
        TextInputEditText priceEt = dialog.findViewById(R.id.priceEt);
        TextInputEditText nameEt = dialog.findViewById(R.id.nameEt);
        TextInputEditText cardNumberEt = dialog.findViewById(R.id.cardNumberEt);
        expiryDateEt = dialog.findViewById(R.id.expiryDateEt);
        TextInputEditText cvcEt = dialog.findViewById(R.id.cvcEt);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);

        priceEt.setText(apartmentModel.getPrice());

        yearsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed while text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Calculate the total price based on the number of years entered
                if (!editable.toString().isEmpty()) {
                    int years = Integer.parseInt(editable.toString());
                    double pricePerYear = Double.parseDouble(apartmentModel.getPrice());
                    double totalPrice = years * pricePerYear;
                    priceEt.setText(String.valueOf(totalPrice));
                } else {
                    // If no years entered, set the price field to empty
                    priceEt.setText(apartmentModel.getPrice());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Set up text change listeners for validation
        cardNumberEt.addTextChangedListener(new CardNumberTextWatcher(cardNumberEt));
        expiryDateEt.addTextChangedListener(new ExpiryDateTextWatcher(expiryDateEt));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String years = yearsEt.getText().toString();
                String price = priceEt.getText().toString();
                String name = nameEt.getText().toString();
                String cardNumber = cardNumberEt.getText().toString();
                String expiryDate = expiryDateEt.getText().toString();
                String cvc = cvcEt.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                String currentDate = sdf.format(new Date());
                if (years.isEmpty() || name.isEmpty() || !validateCardNumber(cardNumber) || expiryDate.length() < 5 || !validateCvc(cvc)) {
                    showMessage("Please enter valid card details");
                } else {
                    if (Integer.valueOf(years) == 0) {
                        showMessage("Please enter valid year number");
                        return;
                    }
                    apartmentModel.setStatus("Booked");
                    apartmentModel.setUserId(String.valueOf(HelperClass.users.getId()));
                    apartmentModel.setYears(years);
                    apartmentModel.setBookingDate(currentDate);
                    databaseHelper.updateApartment(apartmentModel);
                    showMessage("Successfully Booked");
                    dialog.dismiss();
                    finish();
                }
            }
        });

        dialog.show();
    }

    private boolean validateCardNumber(String cardNumber) {
        // Perform card number validation logic
        return !TextUtils.isEmpty(cardNumber) && cardNumber.length() == 19;
    }

    private boolean validateCvc(String cvc) {
        // Perform CVV validation logic
        return !TextUtils.isEmpty(cvc) && cvc.length() == 3;
    }

    private class CardNumberTextWatcher implements TextWatcher {
        private TextInputEditText editText;

        CardNumberTextWatcher(TextInputEditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            String formattedText = formatCardNumber(text);
            editText.removeTextChangedListener(this);
            editText.setText(formattedText);
            editText.setSelection(formattedText.length());
            editText.addTextChangedListener(this);
        }
    }

    private String formatCardNumber(String cardNumber) {
        // Remove any non-digit characters
        String cleanedNumber = cardNumber.replaceAll("\\D", "");

        // Insert space after every 4 digits
        StringBuilder formattedNumber = new StringBuilder();
        for (int i = 0; i < cleanedNumber.length(); i++) {
            formattedNumber.append(cleanedNumber.charAt(i));
            if ((i + 1) % 4 == 0 && i + 1 < cleanedNumber.length()) {
                formattedNumber.append(" ");
            }
        }
        return formattedNumber.toString();
    }

    private class ExpiryDateTextWatcher implements TextWatcher {
        private TextInputEditText editText;

        ExpiryDateTextWatcher(TextInputEditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // You can leave this method empty or add any specific logic you need after text changes
            String text = editable.toString();
            String formattedText = formatExpiryDate(text);
            editText.removeTextChangedListener(this);
            editText.setText(formattedText);
            editText.setSelection(formattedText.length());
            editText.addTextChangedListener(this);
        }
    }

    private String formatExpiryDate(String expiryDate) {
        // Remove any non-digit characters
        String cleanedDate = expiryDate.replaceAll("\\D", "");

        // Check if the cleaned string has a length of 4 and follows the desired format (MM/YY)
        if (!TextUtils.isEmpty(cleanedDate) && cleanedDate.length() == 4 && cleanedDate.matches("^\\d{4}$")) {
            // Extract month and year from the cleaned string
            int month = Integer.parseInt(cleanedDate.substring(0, 2));
            int year = Integer.parseInt(cleanedDate.substring(2));

            // Get the current month and last 2 digits of the current year
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int currentMonth = calendar.get(java.util.Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
            int last2DigitsOfYear = calendar.get(java.util.Calendar.YEAR) % 100;

            // Perform validations
            if (month < 1 || month > 12) {
                showMessage("Please enter a valid month (01-12)");
                cleanedDate = "";
            } else if (year < last2DigitsOfYear || (year == last2DigitsOfYear && month < currentMonth)) {
                showMessage("Please enter a valid expiry date (future date)");
                cleanedDate = "";
            } else {
                // Insert a "/" after the first two digits (month)
                return cleanedDate.substring(0, 2) + "/" + cleanedDate.substring(2);
            }
        }

        // If validations fail, return the original cleaned date
        return cleanedDate;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
