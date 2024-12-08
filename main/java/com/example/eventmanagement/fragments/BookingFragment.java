package com.example.eventmanagement.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.DatabaseHelper;
import com.example.eventmanagement.R;
import com.example.eventmanagement.adapters.BookingsAdapter;
import com.example.eventmanagement.models.Event;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {
    private RecyclerView bookingsRecyclerView;
    private List<Event> bookedEvents;
    private BookingsAdapter bookingsAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookingsRecyclerView = view.findViewById(R.id.bookedEventsRecyclerView);
        databaseHelper = new DatabaseHelper(getContext());
        bookingsAdapter = new BookingsAdapter(getContext(), new ArrayList<Event>());

        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingsRecyclerView.setAdapter(bookingsAdapter);

        loadBookings();
    }

    // Load the events that the user has booked (from the database or shared preferences)
    private void loadBookings() {
        Log.d("BookingFragment", "Inside he function ***************** ");
        bookedEvents = databaseHelper.getBookedEvents();  // Method to get the booked events
        if (bookedEvents != null && !bookedEvents.isEmpty()) {
            for (Event event : bookedEvents) {
                Log.d("BookingFragment", "Booked Event: " + event.toString());
            }
        } else {
            Log.d("BookingFragment", "No booked events found.");
        }
        bookingsAdapter.updateEvents(bookedEvents);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadBookings();  // Reload the bookings when the fragment is resumed
    }

}

