package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {

    // Declare UI elements
    private ImageView eventImageView;
    private TextView eventNameTextView, eventLocationTextView, eventPriceTextView;
    private TextView eventDateTextView, eventTimeTextView, organizerNameTextView, descriptionTextView;
    private Button bookTicketsButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Initialize UI elements
        initializeViews();

        // Retrieve and set event data
        setEventData();

        // Set up back button
        setupBackButton();

        // Set up book tickets button
        setupBookTicketsButton();
    }

    private void initializeViews() {
        eventImageView = findViewById(R.id.event_image);
        eventNameTextView = findViewById(R.id.event_name);
        eventLocationTextView = findViewById(R.id.event_location);
        eventPriceTextView = findViewById(R.id.event_price);
        eventDateTextView = findViewById(R.id.event_date);
        eventTimeTextView = findViewById(R.id.event_time);
        organizerNameTextView = findViewById(R.id.event_organizer);
        descriptionTextView = findViewById(R.id.event_description);
        bookTicketsButton = findViewById(R.id.book_tickets_button);
        backButton = findViewById(R.id.back_button);
    }

    private void setEventData() {
        // Retrieve data passed from HomeFragment
        Intent intent = getIntent();
        String eventName = intent.getStringExtra("name");
        String eventLocation = intent.getStringExtra("location");
        double eventPrice = intent.getDoubleExtra("price", 0.0);
        String eventDate = intent.getStringExtra("date");
        String eventTime = intent.getStringExtra("time");
        String organizerName = intent.getStringExtra("organizer_name");
        int imageResId = intent.getIntExtra("image", R.drawable.default_image);
        String description = intent.getStringExtra("description");


        // Log the received data for debugging
        Log.d("EventDetails", "Received Event Data:");
        Log.d("EventDetails", "Name: " + eventName);
        Log.d("EventDetails", "Location: " + eventLocation);
        Log.d("EventDetails", "Date: " + eventDate);
        Log.d("EventDetails", "Time: " + eventTime);
        Log.d("EventDetails", "Price: " + eventPrice);
        Log.d("EventDetails", "Image Resource ID: " + imageResId);
        Log.d("EventDetails", "Organizer: " + organizerName);
        Log.d("EventDetails", "Description: " + description);

        // Set data to UI elements
        eventImageView.setImageResource(imageResId);
        eventNameTextView.setText(eventName);
        eventLocationTextView.setText(eventLocation);
        eventPriceTextView.setText(getString(R.string.price_format, eventPrice));
        eventDateTextView.setText(getString(R.string.date_format, eventDate));
        eventTimeTextView.setText(getString(R.string.time_format, eventTime));
        organizerNameTextView.setText(getString(R.string.organizer_format, organizerName));
        descriptionTextView.setText(description);
    }

    private void setupBackButton() {
        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Log.d("EventDetailsActivity", "Back button pressed");
                finish();
            }
        };

        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // Set click listener for the back button
        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setupBookTicketsButton() {
        bookTicketsButton.setOnClickListener(v -> {
            // Log the action
            Log.d("EventDetailsActivity", "Book tickets button clicked");

            // Retrieve event details from the Intent
            Intent intent = getIntent();
            String eventName = intent.getStringExtra("name");
            String eventLocation = intent.getStringExtra("location");
            String eventDate = intent.getStringExtra("date");
            double eventPrice = intent.getDoubleExtra("price", 0.0);
            int eventImageResource = intent.getIntExtra("image", R.drawable.default_image);


            String bookingDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


            String bookedBy = "guestUser";

            // Save the booking to the database
            DatabaseHelper databaseHelper = new DatabaseHelper(EventDetailsActivity.this);

            // Use event ID or a unique identifier for each event
            int eventId = intent.getIntExtra("eventId", 1); // Assume you're passing eventId in the intent

            // Save the booking
            if (eventId != -1) {
                databaseHelper.bookEvent(eventName, bookedBy);
                Toast.makeText(EventDetailsActivity.this, "Event booked successfully!", Toast.LENGTH_SHORT).show();

                // pass the event details to the BookingActivity
                Intent bookingIntent = new Intent(EventDetailsActivity.this, BookingActivity.class);
                bookingIntent.putExtra("eventName", eventName);
                bookingIntent.putExtra("eventLocation", eventLocation);
                bookingIntent.putExtra("eventDate", eventDate);
                bookingIntent.putExtra("eventPrice", eventPrice);
                bookingIntent.putExtra("eventImage", eventImageResource);

                // Start the BookingActivity to show the booking confirmation
                startActivity(bookingIntent);
                finish();
            } else {
                Toast.makeText(EventDetailsActivity.this, "Failed to book event.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




