package com.example.eventmanagement.models;

import com.example.eventmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class Interest {
    private String title;
    private int imageResId;

    // Constructor to initialize title and image resource
    public Interest(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for image resource ID
    public int getImageResId() {
        return imageResId;
    }

    // Static method to get a list of interests
    public static List<Interest> getInterests() {
        List<Interest> interests = new ArrayList<>();
        interests.add(new Interest("Cricket", R.drawable.t20match));
        interests.add(new Interest("Music", R.drawable.placeholder_image));
        interests.add(new Interest("Art", R.drawable.art));
        interests.add(new Interest("Dance", R.drawable.dance));
        return interests;
    }
}
