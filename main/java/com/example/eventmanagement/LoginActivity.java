package com.example.eventmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private DatabaseHelper dbHelper;

    // SharedPreferences to store login state
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "EventManagementPrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_EMAIL = "userEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(IS_LOGGED_IN, false)) {
            // Redirect to MainActivity if already logged in
            redirectToMain();
        }

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (dbHelper.isValidUser(email, password)) {
                // Save login state in SharedPreferences
                saveLoginState(email);

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                redirectToMain(); // Navigate to MainActivity
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Show toast if redirected from RegisterActivity
        String registerToastMessage = getIntent().getStringExtra("registerToastMessage");
        if (registerToastMessage != null) {
            Toast.makeText(this, registerToastMessage, Toast.LENGTH_SHORT).show();
        }
    }


    private void saveLoginState(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, true); // Save login status
        editor.putString(USER_EMAIL, email);  // Save user email for future use
        editor.apply(); // Commit changes
    }

    /**
     * Redirect user to MainActivity
     */
    private void redirectToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish(); // Close LoginActivity to prevent going back to it
    }
}
