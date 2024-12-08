package com.example.eventmanagement.models;

public class EventDetails {
        private String name;
        private String image;
        private String date;
        private String place;
        private String organizer;
        private String description;

        // Constructor
        public EventDetails(String name, String image, String date, String place, String organizer, String description) {
            this.name = name;
            this.image = image;
            this.date = date;
            this.place = place;
            this.organizer = organizer;
            this.description = description;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


}
