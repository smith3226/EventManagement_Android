package com.example.eventmanagement.utils;

import android.content.Context;

import com.example.eventmanagement.models.EventDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static List<EventDetails> loadEventsFromAssets(Context context) {
        List<EventDetails> eventList = new ArrayList<>();
        try {
            // Load the JSON file from assets
            InputStream is = context.getAssets().open("events.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert byte array to string
            String json = new String(buffer, "UTF-8");

            // Parse the JSON string
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eventObject = jsonArray.getJSONObject(i);
                String name = eventObject.getString("name");
                String image = eventObject.getString("image");
                String date = eventObject.getString("date");
                String place = eventObject.getString("place");
                String organizer = eventObject.getString("organizer");
                String description = eventObject.getString("description");

                // Create Event object and add to the list
                EventDetails event = new EventDetails(name, image, date, place, organizer, description);
                eventList.add(event);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return eventList;
    }


}
