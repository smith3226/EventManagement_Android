package com.example.eventmanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.eventmanagement.models.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "event_management_new.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ORGANIZER_NAME = "organizer_name";
    public static final String COLUMN_TICKETS_AVAILABLE = "tickets_available";
    public static final String COLUMN_CATEGORY = "category";


    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_TIME + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_IMAGE + " INTEGER, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_ORGANIZER_NAME + " TEXT, " +
            COLUMN_TICKETS_AVAILABLE + " INTEGER, " +
            COLUMN_CATEGORY + " TEXT)";





    // Bookings table
    public static final String TABLE_BOOKINGS = "bookings";
    public static final String COLUMN_EVENT_ID = "event_id";  // Foreign key reference
    public static final String COLUMN_BOOKED_BY = "booked_by";
    public static final String COLUMN_BOOKED_DATE = "booked_date";
    public static final String COLUMN_EVENT_NAME = "event_name"; // Added event_name column

    // Create Bookings table SQL query
    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKINGS + " (" +
            COLUMN_EVENT_ID + " INTEGER, " +
            COLUMN_BOOKED_BY + " TEXT, " +
            COLUMN_BOOKED_DATE + " TEXT, " +
            COLUMN_EVENT_NAME + " TEXT, " + // event_name column
            "PRIMARY KEY(" + COLUMN_BOOKED_BY + ", " + COLUMN_BOOKED_DATE + "), " + // Composite primary key
            "FOREIGN KEY(" + COLUMN_EVENT_ID + ") REFERENCES events(id) ON DELETE CASCADE" + // Foreign key reference to events table
            ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);




    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "onCreate called");
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_BOOKINGS); // Create bookings table
        db.execSQL(CREATE_TABLE_USERS);


        //Insert Events

        insertSampleEvents();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_EVENTS + " ADD COLUMN " + COLUMN_ORGANIZER_NAME + " TEXT");
            db.execSQL(CREATE_TABLE_BOOKINGS);
        }
        onCreate(db);
    }

    public boolean isEventsTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0; // returns true if the table is empty, false otherwise
    }


    // Modified insertEvent method to handle database connection safely
    public void insertEvent(String name, String location, double price, String time, String date,
                            int imageResourceId, String description, String organizerName,
                            int ticketsAvailable, String category) {
        Log.d("DatabaseHelper", "Insert event called");
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_LOCATION, location);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_TIME, time);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_IMAGE, imageResourceId);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_ORGANIZER_NAME, organizerName);
            values.put(COLUMN_TICKETS_AVAILABLE, ticketsAvailable);
            values.put(COLUMN_CATEGORY, category);

            long eventId = db.insert(TABLE_EVENTS, null, values);
            long result = db.insert(TABLE_EVENTS, null, values);
            if (result != -1) {
                Log.d("DatabaseHelper", "Event inserted successfully: " + name);
            } else {
                Log.e("DatabaseHelper", "Failed to insert event: " + name);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting event", e);
        } finally {
            if (db != null) {

                db.close();
            }
        }
    }

    // Method to insert sample events separately
    public void insertSampleEventsIfEmpty() {
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int count = cursor.getInt(0);
                cursor.close();

                // insert sample events if the table is empty
                if (count == 0) {
                    insertSampleEvents();
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error checking events table", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public void insertSampleEvents() {
        Log.d("++++++++++++++", "Inside Insert sample Events +++++++++");
        insertEvent("Dance Fiesta", "Dance Arena, Los Angeles", 50.0, "8:00 PM", "2024-12-25", R.drawable.dance, "Experience the thrill of live dance performances.", "Dance Masters Inc.", 200, "Dance");
        insertEvent("Music Festival", "Open Grounds, Chicago", 40.0, "6:00 PM", "2024-12-30", R.drawable.music_festival, "Enjoy music from the best bands and artists.", "Chicago Music Society", 500, "Music");
        insertEvent("Art Exhibition", "Art Gallery, San Francisco", 20.0, "4:00 PM", "2025-01-05", R.drawable.art, "An exhibition showcasing contemporary art.", "Artists Guild", 50, "Art");
        insertEvent("T20 Finals", "Melbourne Cricket Ground, Australia", 150.0, "7:30 PM", "2024-12-20", R.drawable.t20match, "The ultimate showdown of cricket champions.", "Cricket Australia", 1000, "Sports");
        insertEvent("Samay Raina Unfiltered", "Toronto Canada", 120.0, "3:30 PM", "2025-02-23", R.drawable.sraina, "An evening of laughter with Samay Raina.", "Comedy Nights Canada", 300, "Comedy");
        insertEvent("Cultural Night Gala", "City Hall, Vancouver", 30.0, "7:00 PM", "2025-02-15", R.drawable.cultural_night, "Celebrating diverse cultures with music and dance.", "Vancouver Cultural Association", 150, "Cultural");
        insertEvent("Startup Summit", "Tech Park, Silicon Valley", 200.0, "10:00 AM", "2025-03-05", R.drawable.startup_summit, "Networking and learning from industry leaders.", "Tech Hub", 500, "Business");


        // Additional events
        insertEvent("Fashion Show Extravaganza", "Grand Ballroom, Paris", 100.0, "9:00 PM", "2025-01-10", R.drawable.fashion_show, "Experience the latest in fashion and design.", "Fashionistas Worldwide", 300, "Fashion");
        insertEvent("Food Festival", "Downtown Park, New York", 15.0, "12:00 PM", "2025-01-15", R.drawable.food_festival, "Taste the best dishes from around the world.", "NYC Foodies", 200, "Food");
        insertEvent("Tech Innovation Expo", "Convention Center, San Francisco", 50.0, "10:00 AM", "2025-02-01", R.drawable.tech_expo, "Explore the latest in technology and innovation.", "Tech Innovators", 400, "Tech");
        insertEvent("Standup Comedy Night", "Comedy Club, New York", 25.0, "7:30 PM", "2024-12-20", R.drawable.standup, "A night of laughter with top stand-up comedians.", "Laughs United", 100, "Comedy");
        insertEvent("Yoga Retreat", "Mountain Resort, Colorado", 250.0, "8:00 AM", "2025-02-10", R.drawable.yoga_retreat, "Relax and rejuvenate at this peaceful retreat.", "Mountain Serenity", 50, "Health");
        insertEvent("Theater Performance", "National Theater, London", 75.0, "6:30 PM", "2025-03-01", R.drawable.theater, "A classic theater performance for all ages.", "London Theater Company", 300, "Theater");
        insertEvent("Gastronomy Workshop", "Cooking School, Florence", 120.0, "3:00 PM", "2025-03-12", R.drawable.gastronomy_workshop, "Learn to cook like a professional chef.", "Gastronomers Guild", 50, "Food");
        insertEvent("Ultimate Basketball Championship", "Madison Square Garden, New York", 60.0, "8:00 PM", "2025-03-10", R.drawable.basketball, "The most exciting basketball tournament of the year.", "NBA League", 800, "Sports");
        insertEvent("Photography Exhibition", "City Museum, Berlin", 25.0, "5:00 PM", "2025-03-20", R.drawable.photography_exhibition, "Showcasing stunning photographs from around the world.", "Photo Gallery", 100, "Art");


        insertDanceEvents();

        // Music Events
        insertMusicEvents();

        // Art Events
        insertArtEvents();

        // Sports Events
        insertSportsEvents();

        // Comedy Events
        insertComedyEvents();

        // Cultural Events
        insertCulturalEvents();

        // Business Events
        insertBusinessEvents();

        // Food Events
        insertFoodEvents();

        // Fashion Events
        insertFashionEvents();

        // Health Events
        insertHealthEvents();



    }


    private void insertDanceEvents() {
        insertEvent("Dance Fiesta", "Dance Arena, Los Angeles", 50.0, "8:00 PM", "2024-12-25", R.drawable.dance, "Experience the thrill of live dance performances.", "Dance Masters Inc.", 200, "Dance");
        insertEvent("Standup Comedy Night", "Comedy Club, New York", 25.0, "7:30 PM", "2024-12-20", R.drawable.standup, "A night of laughter with top stand-up comedians.", "Laughs United", 100, "Dance");
    }

    private void insertMusicEvents() {
        insertEvent("Music Festival", "Open Grounds, Chicago", 40.0, "6:00 PM", "2024-12-30", R.drawable.music_festival, "Enjoy music from the best bands and artists.", "Chicago Music Society", 500, "Music");
        insertEvent("Summer Music Carnival", "Central Park, New York", 40.0, "1:00 PM", "2025-04-10", R.drawable.summer_music, "A carnival filled with live music, food, and fun.", "NYC Events", 600, "Music");
    }

    private void insertArtEvents() {
        insertEvent("Art Exhibition", "Art Gallery, San Francisco", 20.0, "4:00 PM", "2025-01-05", R.drawable.art, "An exhibition showcasing contemporary art.", "Artists Guild", 50, "Art");
        insertEvent("Photography Exhibition", "City Museum, Berlin", 25.0, "5:00 PM", "2025-03-20", R.drawable.photography_exhibition, "Showcasing stunning photographs from around the world.", "Photo Gallery", 100, "Art");
    }

    private void insertSportsEvents() {
        insertEvent("T20 Finals", "Melbourne Cricket Ground, Australia", 150.0, "7:30 PM", "2024-12-20", R.drawable.t20match, "The ultimate showdown of cricket champions.", "Cricket Australia", 1000, "Sports");
        insertEvent("Ultimate Basketball Championship", "Madison Square Garden, New York", 60.0, "8:00 PM", "2025-03-10", R.drawable.basketball, "The most exciting basketball tournament of the year.", "NBA League", 800, "Sports");
    }

    private void insertComedyEvents() {
        insertEvent("Samay Raina Unfiltered", "Toronto Canada", 120.0, "3:30 PM", "2025-02-23", R.drawable.sraina, "An evening of laughter with Samay Raina.", "Comedy Nights Canada", 300, "Comedy");
    }

    private void insertCulturalEvents() {
        insertEvent("Cultural Night Gala", "City Hall, Vancouver", 30.0, "7:00 PM", "2025-02-15", R.drawable.cultural_night, "Celebrating diverse cultures with music and dance.", "Vancouver Cultural Association", 150, "Cultural");
    }

    private void insertBusinessEvents() {
        insertEvent("Startup Summit", "Tech Park, Silicon Valley", 200.0, "10:00 AM", "2025-03-05", R.drawable.startup_summit, "Networking and learning from industry leaders.", "Tech Hub", 500, "Business");
    }

    private void insertFoodEvents() {
        insertEvent("Food Festival", "Downtown Park, New York", 15.0, "12:00 PM", "2025-01-15", R.drawable.food_festival, "Taste the best dishes from around the world.", "NYC Foodies", 200, "Food");
        insertEvent("Gastronomy Workshop", "Cooking School, Florence", 120.0, "3:00 PM", "2025-03-12", R.drawable.gastronomy_workshop, "Learn to cook like a professional chef.", "Gastronomers Guild", 50, "Food");
    }

    private void insertFashionEvents() {
        insertEvent("Fashion Show Extravaganza", "Grand Ballroom, Paris", 100.0, "9:00 PM", "2025-01-10", R.drawable.fashion_show, "Experience the latest in fashion and design.", "Fashionistas Worldwide", 300, "Fashion");
    }

    private void insertHealthEvents() {
        insertEvent("Yoga Retreat", "Mountain Resort, Colorado", 250.0, "8:00 AM", "2025-02-10", R.drawable.yoga_retreat, "Relax and rejuvenate at this peaceful retreat.", "Mountain Serenity", 50, "Health");
    }

    public List<Event> getEventsByCategory(String category) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE category = ?", new String[]{category});
        if (cursor.moveToFirst()) {
            do {
                // Fetching all columns for each event
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String organizerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER_NAME));
                int ticketsAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TICKETS_AVAILABLE));
                String categoryDb = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                events.add(new Event(name, location, price, time, date, imageResourceId, description, organizerName, ticketsAvailable, categoryDb));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    public List<Event> getTrendingEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE tickets_available > ? ORDER BY tickets_available DESC LIMIT 5", new String[]{"300"});
        if (cursor.moveToFirst()) {
            do {
                // Fetching all columns for each event
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String organizerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER_NAME));
                int ticketsAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TICKETS_AVAILABLE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                events.add(new Event(name, location, price, time, date, imageResourceId, description, organizerName, ticketsAvailable, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        if (cursor.moveToFirst()) {
            do {
                // Fetching all columns for each event
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String organizerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER_NAME));
                int ticketsAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TICKETS_AVAILABLE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                events.add(new Event(name, location, price, time, date, imageResourceId, description, organizerName, ticketsAvailable, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }



    //get Event by name
    public Event getEventByName(String eventName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Event event = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_NAME + " = ?", new String[]{eventName});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                int imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String organizerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER_NAME));
                int ticketsAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TICKETS_AVAILABLE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                event = new Event(name, location, price, time, date, imageResourceId, description, organizerName, ticketsAvailable, category);
            } catch (IllegalArgumentException e) {
                Log.e("DatabaseHelper", "Column not found or Cursor is empty: " + e.getMessage());
            }
        } else {
            Log.d("DatabaseHelper", "No event found with name: " + eventName);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.d("Event name", "event");
        return event;
    }



    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Save a booked event in the database
    public void bookEvent(String eventName, String bookedBy) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Now insert into the bookings table with the event name
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKED_BY, bookedBy);  // Store the username (e.g., "Smith")
        values.put(COLUMN_BOOKED_DATE, getCurrentDate());  // Store the booking date (you can modify this method to get today's date)
        values.put("event_name", eventName);  // Store the event name (instead of eventId)

        // Insert into bookings table
        long result = db.insert(TABLE_BOOKINGS, null, values);
        if (result == -1) {
            Log.d("DatabaseHelper", "Booking failed for event: " + eventName);
        } else {
            Log.d("DatabaseHelper", "Event " + eventName + " booked by " + bookedBy);
        }
        db.close();
    }


    public List<Event> getBookedEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> bookedEvents = new ArrayList<>();

        // Query the bookings table to get the booked events
        String query = "SELECT " + COLUMN_BOOKED_BY + ", " + COLUMN_BOOKED_DATE + ", event_name FROM " + TABLE_BOOKINGS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Extract information from the cursor for bookings
                @SuppressLint("Range") String bookedBy = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKED_BY));
                @SuppressLint("Range") String bookedDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKED_DATE));

                // Extract the event name from the bookings table
                @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex("event_name"));

                // Check if the event name is valid before calling getEventByName()
                if (eventName != null && !eventName.isEmpty()) {
                    // Use the event name to get the event details
                    Event event = getEventByName(eventName);

                    // Add the event to the list if it's not null
                    if (event != null) {
                        bookedEvents.add(event);  // Add the event to the list of booked events
                    }
                } else {
                    Log.e("getBookedEvents", "Event name is missing or invalid for booked by: " + bookedBy);
                }
            }
            cursor.close();  // Close the cursor to avoid memory leaks
        } else {
            Log.e("getBookedEvents", "Cursor is null, could not retrieve data.");
        }
        db.close();  // Always close the database connection
        return bookedEvents;  // Return the list of booked events
    }





    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_EMAIL + " TEXT PRIMARY KEY, " +
            COLUMN_USER_NAME + " TEXT, " +
            COLUMN_USER_PASSWORD + " TEXT)";


    // Register user
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Validate user
    public boolean isValidUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ? AND password = ?",
                new String[]{email, password});
        Log.d("Logged in User" , "User email " + email);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }




}















