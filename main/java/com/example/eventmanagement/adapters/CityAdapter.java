package com.example.eventmanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.R;
import com.example.eventmanagement.models.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cities;

    // Constructor
    public CityAdapter() {
        this.cities = City.getCities(); // Use City.getCities() to get the static data
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        // Get the current city at the given position
        City city = cities.get(position);

        // Bind the data to the views
        holder.cityNameTextView.setText(city.getName());
        holder.cityImageView.setImageResource(city.getImageResId());
    }

    @Override
    public int getItemCount() {
        // Return the size of the city list
        return cities.size();
    }

    // ViewHolder class to hold the views for each item
    static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        ImageView cityImageView;

        // Constructor to initialize the views
        CityViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.item_title);
            cityImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
