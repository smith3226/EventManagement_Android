package com.example.eventmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventmanagement.fragments.BookingFragment;
import com.example.eventmanagement.fragments.HomeFragment;
import com.example.eventmanagement.fragments.ProfileFragment;
import com.example.eventmanagement.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE =1001 ;
    private ImageView profileImage;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        profileImage = findViewById(R.id.profileImage);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;

            // Check which item was clicked and log it
            if (itemId == R.id.nav_home) {
                Log.d("BottomNavigation", "Home button clicked");

                // Check if HomeFragment is already the current fragment
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment)) {
                    selectedFragment = new HomeFragment();
                    replaceFragment(selectedFragment);
                }
                return true;
            } else if (itemId == R.id.nav_search) {
                Log.d("BottomNavigation", "Search button clicked");
                selectedFragment = new SearchFragment();
                replaceFragment(selectedFragment); // Load SearchFragment
                return true;
            } else if (itemId == R.id.nav_booking) {
                Log.d("BottomNavigation", "Booking button clicked");
                selectedFragment = new BookingFragment();
                replaceFragment(selectedFragment);
                // Load BookingFragment
                return true;
            } else if (itemId == R.id.nav_profile) {
                Log.d("BottomNavigation", "Profile button clicked");
                // Load ProfileFragment if logged in, else navigate to login
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    selectedFragment = new ProfileFragment();
                } else {
                    // Redirect to LoginActivity if not logged in
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                replaceFragment(selectedFragment);
                return true;
            }
            return false;
        });

        // Load the initial fragment (HomeFragment)
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            replaceFragment(homeFragment); // Using the replaceFragment method here
        }

        // Set click listener for profile image
        profileImage.setOnClickListener(v -> showProfileMenu(v));
    }

    // Helper method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);  // Optional: Add to back stack to allow navigation back
        fragmentTransaction.commit();
    }

    // Method to show the PopupMenu when profile image is clicked
    private void showProfileMenu(View view) {
        // Check if the user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", true);

        // Create a new PopupMenu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);

        // Inflate the menu based on login status
        if (isLoggedIn) {
            popupMenu.getMenu().add("Logout");
        } else {
            popupMenu.getMenu().add("Login");
        }

        // Set a listener for when an option is selected
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("Logout")) {
                // Handle logout
                sharedPreferences.edit().putBoolean("isLoggedIn", false).apply();

                // Clear any other user-specific data (e.g., user profile info, authentication tokens)
                sharedPreferences.edit().remove("userToken").apply();  // If you have a token or other data to clear

                // Navigate the user to the login screen
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Log.d("Logout Clicked" , "I am inside logout");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish current activity so user cannot go back

                Log.d("ProfileMenu", "User logged out");

                // Show a toast message for confirmation
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            } else if (item.getTitle().equals("Login")) {
                // Navigate to the login activity if not logged in
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            return true;
        });

        // Show the menu
        popupMenu.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}




