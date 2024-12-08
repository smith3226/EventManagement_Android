package com.example.eventmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.R;
import com.example.eventmanagement.models.Event;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingViewHolder> {

    private Context context;
    private List<Event> bookedEvents;

    public BookingsAdapter(Context context, List<Event> bookedEvents) {
        this.context = context;
        this.bookedEvents = bookedEvents;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Event event = bookedEvents.get(position);
        holder.eventName.setText(event.getEventName());
        holder.eventDate.setText(event.getEventDate());
        holder.eventLocation.setText(event.getEventLocation());
        holder.eventPrice.setText(String.format("$%.2f", event.getPrice()));
    }

    @Override
    public int getItemCount() {
        return bookedEvents.size();
    }

    // Method to update the events list in the adapter
    public void updateEvents(List<Event> newEvents) {
        bookedEvents.clear();
        bookedEvents.addAll(newEvents);
        notifyDataSetChanged();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventDate, eventLocation, eventPrice;

        public BookingViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventPrice = itemView.findViewById(R.id.event_price);
        }
    }
}
