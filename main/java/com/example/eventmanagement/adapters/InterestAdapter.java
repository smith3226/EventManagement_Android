package com.example.eventmanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.R;
import com.example.eventmanagement.models.Interest;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.GenericViewHolder> {

    private List<Interest> interests;
    private OnInterestClickListener listener;  // Listener to handle item clicks

    // Constructor accepts a listener to handle click events
    public InterestAdapter(List<Interest> interests, OnInterestClickListener listener) {
        this.interests = interests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        // Get the current interest at the given position
        Interest interest = interests.get(position);

        // Bind the data to the views
        holder.titleTextView.setText(interest.getTitle());
        holder.imageView.setImageResource(interest.getImageResId());

        // Set click listener on the item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onInterestClick(interest.getTitle());  // Notify the fragment with the selected category
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the interest list
        return interests.size();
    }

    // ViewHolder class to hold the views for each item
    static class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        // Constructor to initialize the views
        GenericViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    // Interface to handle interest item clicks
    public interface OnInterestClickListener {
        void onInterestClick(String category);  // Method to pass the selected category to the fragment
    }
}


//package com.example.eventmanagement.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.eventmanagement.R;
//import com.example.eventmanagement.models.Interest;
//
//import java.util.List;
//
//public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.GenericViewHolder> {
//
//    private List<Interest> interests;
//
//    // Constructor
//    public InterestAdapter() {
//        this.interests = Interest.getInterests();  // Use Interest.getInterests() to get the static data
//    }
//
//    @NonNull
//    @Override
//    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate the item layout
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic, parent, false);
//        return new GenericViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
//        // Get the current interest at the given position
//        Interest interest = interests.get(position);
//
//        // Bind the data to the views
//        holder.titleTextView.setText(interest.getTitle());
//        holder.imageView.setImageResource(interest.getImageResId());
//    }
//
//    @Override
//    public int getItemCount() {
//        // Return the size of the interest list
//        return interests.size();
//    }
//
//    // ViewHolder class to hold the views for each item
//    static class GenericViewHolder extends RecyclerView.ViewHolder {
//        TextView titleTextView;
//        ImageView imageView;
//
//        // Constructor to initialize the views
//        GenericViewHolder(View itemView) {
//            super(itemView);
//            titleTextView = itemView.findViewById(R.id.item_title);
//            imageView = itemView.findViewById(R.id.item_image);
//        }
//    }
//}
