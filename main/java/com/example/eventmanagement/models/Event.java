package com.example.eventmanagement.models;


public class Event {
    // Private fields for event details
    private String name; // Name of the event
    private String location; // Location of the event
    private double price; // Price of the event ticket
    private String time; // Time of the event
    private String date; // Date of the event
    private int imageResourceId; // Resource ID for event image (drawable)
    private String description; // Description of the event
    private String organizerName; // Organizer's name
    private int ticketsAvailable; // Number of tickets available
    private String category; // Category of the event (e.g., Music, Tech, Sports)

    // Constructor
    public Event(String name, String location, double price, String time, String date, int imageResourceId,
                 String description, String organizerName, int ticketsAvailable, String category) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.time = time;
        this.date = date;
        this.imageResourceId = imageResourceId;
        this.description = description;
        this.organizerName = organizerName;
        this.ticketsAvailable = ticketsAvailable;
        this.category = category;

    }

    // Default Constructor
    public Event() {
        // Empty constructor for cases where you may initialize an object and set fields later
    }

    // Getters and Setters for each field

    public String getEventName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Overriding toString() for easier debugging
    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", description='" + description + '\'' +
                ", organizerName='" + organizerName + '\'' +
                ", ticketsAvailable=" + ticketsAvailable +
                ", category='" + category + '\'' +
                '}';
    }


}



