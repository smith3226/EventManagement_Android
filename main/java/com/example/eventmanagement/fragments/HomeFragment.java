package com.example.eventmanagement.fragments;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.eventmanagement.DatabaseHelper;
import com.example.eventmanagement.R;
import com.example.eventmanagement.adapters.EventsAdapter;
import com.example.eventmanagement.models.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private RecyclerView trendingEventsRecycler, categoriesRecycler, upcomingEventsRecycler;
    private EventsAdapter trendingEventsAdapter, categoriesAdapter, upcomingEventsAdapter;
    private List<Event> trendingEventsList, categoriesList, upcomingEventsList;
    private DatabaseHelper dbHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d("HomeFragment", "You are in the Home screen");

        // Initialize RecyclerViews
        trendingEventsRecycler = view.findViewById(R.id.trending_events_recycler);
        categoriesRecycler = view.findViewById(R.id.categories_recycler);
        upcomingEventsRecycler = view.findViewById(R.id.upcoming_events_recycler);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(requireContext());

        // Clear old data and re-insert sample events (only if required)
        dbHelper.insertSampleEvents();

        // Fetch all events from the database
        List<Event> allEvents = dbHelper.getAllEvents();
        Log.d("HomeFragment", "Number of events fetched from database: " + allEvents.size());

        // Prepare lists for each RecyclerView
        prepareEventLists(allEvents);

        // Set up adapters for RecyclerViews
        trendingEventsAdapter = new EventsAdapter(requireContext(), trendingEventsList);
        categoriesAdapter = new EventsAdapter(requireContext(), categoriesList);
        upcomingEventsAdapter = new EventsAdapter(requireContext(), upcomingEventsList);

        // Set up LayoutManager and Adapter for each RecyclerView
        setUpRecyclerView(trendingEventsRecycler, trendingEventsAdapter, LinearLayoutManager.HORIZONTAL);
        setUpRecyclerView(categoriesRecycler, categoriesAdapter, LinearLayoutManager.HORIZONTAL);
        setUpRecyclerView(upcomingEventsRecycler, upcomingEventsAdapter, LinearLayoutManager.VERTICAL);

        return view;
    }

    // Utility method to set up a RecyclerView
    private void setUpRecyclerView(RecyclerView recyclerView, EventsAdapter adapter, int orientation) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), orientation, false));
        recyclerView.setAdapter(adapter);
    }

    private void prepareEventLists(List<Event> allEvents) {
        // Initialize lists
        trendingEventsList = new ArrayList<>();
        categoriesList = new ArrayList<>();
        upcomingEventsList = new ArrayList<>();

        // Trending Events
        if (allEvents.size() > 1) {
            trendingEventsList.add(allEvents.get(7));  // First event as trending
            trendingEventsList.add(allEvents.get(5));  // Second event as trending
        }

        // Categories
        for (int i = 0; i < Math.min(5, allEvents.size()); i++) {
            categoriesList.add(allEvents.get(i));
        }

        // Upcoming Events - Add all events as upcoming (based on the assumption that they are upcoming)
        Random random = new Random();
        int randomStartIndex = random.nextInt(allEvents.size() - 4);
        for (int i = randomStartIndex; i <randomStartIndex + 4; i++) {
            upcomingEventsList.add(allEvents.get(i));
        }


        Log.d("HomeFragment", "Trending Events: " + trendingEventsList.size());
        Log.d("HomeFragment", "Categories: " + categoriesList.size());
        Log.d("HomeFragment", "Upcoming Events: " + upcomingEventsList.size());
    }

}
