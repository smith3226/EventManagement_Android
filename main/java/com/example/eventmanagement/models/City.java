package com.example.eventmanagement.models;

import com.example.eventmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class City {

    private String name;
    private int imageResId;

    // Constructor to initialize name and image resource ID
    public City(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getter for city name
    public String getName() {
        return name;
    }

    // Getter for image resource ID
    public int getImageResId() {
        return imageResId;
    }

    // Static method to get a list of cities
    public static List<City> getCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("New York", R.drawable.city_new_york));
        cities.add(new City("Los Angeles", R.drawable.city_new_york));
        cities.add(new City("Toronto", R.drawable.city_new_york));
        cities.add(new City("London", R.drawable.city_new_york));
        return cities;
    }
}
