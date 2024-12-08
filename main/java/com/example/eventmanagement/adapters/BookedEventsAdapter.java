
package com.example.eventmanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.R;
import com.example.eventmanagement.models.Event;

import java.util.List;

public class BookedEventsAdapter extends RecyclerView.Adapter<BookedEventsAdapter.EventViewHolder> {

    private List<Event> bookedEvents;

    // Constructor to receive the list of booked events
    public BookedEventsAdapter(List<Event> bookedEvents) {
        this.bookedEvents = bookedEvents;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each event
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booked_event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Get the event data for the current position
        Event event = bookedEvents.get(position);

        // Bind event data to the views
        holder.eventNameTextView.setText(event.getEventName());
        holder.eventDateTextView.setText(event.getEventDate());

        // Set the event image (You can modify this if you have an actual image resource ID)
        holder.eventImageView.setImageResource(event.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return bookedEvents.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImageView;
        TextView eventNameTextView, eventDateTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
        }
    }
}
