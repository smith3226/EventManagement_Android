package com.example.eventmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {

    private TextView eventNameTextView, eventLocationTextView, eventDateTextView, eventPriceTextView, confirmationMessageTextView;
    private ImageView eventImageView, qrCodeImageView;

    private Button  backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking);


        //initialize back button
        backButton = findViewById(R.id.back_button);

        // Set up back button
        setupBackButton();

        // Initialize views
        eventNameTextView = findViewById(R.id.eventName);
        eventLocationTextView = findViewById(R.id.eventLocation);
        eventDateTextView = findViewById(R.id.eventDate);
        eventPriceTextView = findViewById(R.id.eventPrice);
        confirmationMessageTextView = findViewById(R.id.confirmationMessage);
        eventImageView = findViewById(R.id.eventImage);
        qrCodeImageView = findViewById(R.id.qrCodeImage);

        // Get the event details from the Intent
        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String eventLocation = intent.getStringExtra("eventLocation");
        String eventDate = intent.getStringExtra("eventDate");
        double eventPrice = intent.getDoubleExtra("eventPrice", 0);
        int eventImageResource = intent.getIntExtra("eventImage", 0);

        // Set the event details in the views
        eventNameTextView.setText(eventName);
        eventLocationTextView.setText(eventLocation);
        eventDateTextView.setText(eventDate);
        eventPriceTextView.setText("Price: $" + eventPrice);
        eventImageView.setImageResource(eventImageResource);
        confirmationMessageTextView.setText("Your tickets have been booked for " + eventName);

        // Generate a random booking reference for the QR code
        String bookingReference = generateRandomBookingReference();

        // Generate the QR code for the booking reference
        generateQRCode(bookingReference);
    }

    // Generate a random booking reference number
    private String generateRandomBookingReference() {
        Random random = new Random();
        return "BOOK" + random.nextInt(1000000);
    }

    // Generate QR code for the booking reference
    private void generateQRCode(String bookingReference) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, String> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, "1");
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(bookingReference, BarcodeFormat.QR_CODE, 400, 400, hintMap));
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }

    // Convert the encoded QR code to a Bitmap image
    private Bitmap toBitmap(com.google.zxing.common.BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Set black or white pixels
            }
        }
        return bitmap;
    }


    private void setupBackButton() {
        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Add any custom logic here before going back
                Log.d("EventDetailsActivity", "Back button pressed");
                finish(); // Close this activity and return to the previous one
            }
        };

        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // Set click listener for the back button
        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

}
