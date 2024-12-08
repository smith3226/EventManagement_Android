package com.example.eventmanagement.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.R;
import com.example.eventmanagement.adapters.CityAdapter;
import com.example.eventmanagement.adapters.EventsAdapter;
import com.example.eventmanagement.adapters.InterestAdapter;
import com.example.eventmanagement.DatabaseHelper;
import com.example.eventmanagement.models.Event;
import com.example.eventmanagement.models.Interest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {
    private RecyclerView interestsRecyclerView, citiesRecyclerView, currentlyHappeningRecyclerView;
    private EditText searchBar;
    private List<Event> allEvents;
    private List<Event> filteredEvents;
    private DatabaseHelper databaseHelper;  // DatabaseHelper to fetch events by category
    private EventsAdapter eventsAdapter;    // Adapter to display filtered events

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        searchBar = view.findViewById(R.id.search_bar);
        interestsRecyclerView = view.findViewById(R.id.interests_recycler_view);
        citiesRecyclerView = view.findViewById(R.id.cities_recycler_view);
        currentlyHappeningRecyclerView = view.findViewById(R.id.currently_happening_recycler_view);

        // Initialize the DatabaseHelper and EventsAdapter
        databaseHelper = new DatabaseHelper(getContext());
        eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
        filteredEvents = new ArrayList<>();

        // Setup RecyclerViews
        setupRecyclerViews();

        // Setup search bar functionality (filter events by name)
        setupSearchBar();


        //load all events
        loadAllEvents();
    }


    //method to load all events
    private void loadAllEvents() {
        // Fetch all events from the database
        allEvents = databaseHelper.getAllEvents();
        filteredEvents = new ArrayList<>();
        eventsAdapter.updateEvents(filteredEvents);

        Log.d("SearchFragment", "Loaded total events: " + allEvents.size());
    }

    private void setupRecyclerViews() {
        // Setup the Interests RecyclerView
        interestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        interestsRecyclerView.setAdapter(new InterestAdapter(getInterestsList(), new InterestAdapter.OnInterestClickListener() {
            @Override
            public void onInterestClick(String category) {
                // Fetch events by category when an interest is clicked
                filterEventsByCategory(category);
            }
        }));

        // Setup the Cities RecyclerView (Assuming this is a separate functionality)
        citiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        citiesRecyclerView.setAdapter(new CityAdapter()); // Implement this adapter as per your requirement

        // Setup the Currently Happening RecyclerView
        currentlyHappeningRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        currentlyHappeningRecyclerView.setAdapter(eventsAdapter);  // Set the EventsAdapter for displaying events
    }

    private void setupSearchBar() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("SearchFragment", "Before text change: " + s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SearchFragment", "On text change: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("SearchFragment", "After text changed: " + s.toString());
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    // Filter events by name (or other criteria) if the search bar is not empty
                    filterEventsByQuery(query);
                } else {
                    // Reset the event list if the search query is empty
                    filteredEvents.clear();
                    eventsAdapter.updateEvents(filteredEvents);

                }
            }
        });
    }

    // Sample list of interests
    private List<Interest> getInterestsList() {
        return Interest.getInterests();
    }

    // Method to filter events based on the selected interest (category)
    private void filterEventsByCategory(String category) {
        List<Event> eventsByCategory = databaseHelper.getEventsByCategory(category);
        filteredEvents = new ArrayList<>(eventsByCategory);
        eventsAdapter.updateEvents(filteredEvents);
    }

    // Method to filter events based on the search query
    private void filterEventsByQuery(String query) {
        Log.d("Filter event", "I am inside query");

        filteredEvents.clear();
        filteredEvents = allEvents.stream()
                .filter(event ->
                        event.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                                event.getEventName().toLowerCase().contains(query.toLowerCase())
                )
                .collect(Collectors.toList());

        Log.d("SearchFragment", "Filtered events count: " + filteredEvents.size());

        // Update the adapter on the main thread
        requireActivity().runOnUiThread(() -> {
            eventsAdapter.updateEvents(filteredEvents);
        });



    }
}
