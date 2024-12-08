package com.example.eventmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventmanagement.EventDetailsActivity;
import com.example.eventmanagement.R;
import com.example.eventmanagement.models.Event;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventsAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    public void updateEvents(List<Event> newEventList) {
        this.eventList.clear();
        this.eventList = newEventList;
        notifyDataSetChanged();  // Notify the adapter to refresh the RecyclerView
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Set text fields
        holder.eventName.setText(event.getEventName());
        holder.eventDate.setText(event.getEventDate());
        holder.eventLocation.setText(event.getEventLocation());

        // Load the image directly from resources
        holder.eventImage.setImageResource(event.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            // Pass all the required event details to the event details activity
            intent.putExtra("name", event.getEventName());
            intent.putExtra("image", event.getImageResourceId());
            intent.putExtra("date", event.getEventDate());
            intent.putExtra("time", event.getTime());
            intent.putExtra("location", event.getEventLocation());
            intent.putExtra("price", event.getPrice());
            intent.putExtra("organizer_name", event.getOrganizerName());
            intent.putExtra("description", event.getDescription());

            // Start the event details activity
            context.startActivity(intent);
        });
    }




    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView eventDate;
        TextView eventLocation;
        ImageView eventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventImage = itemView.findViewById(R.id.event_image);

        }
    }
}
