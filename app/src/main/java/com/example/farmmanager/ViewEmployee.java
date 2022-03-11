package com.example.farmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class ViewEmployee extends AppCompatActivity {
    TextView editProfile, assignActivity, viewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        Bundle bundle = getIntent().getExtras();
        String firstName, lastName, id, phone;
        firstName = bundle.getString("firstName");
        lastName = bundle.getString("lastName");
        id = bundle.getString("id");
        phone = bundle.getString("contact");
        String employee = String.format("%s %s", firstName, lastName);
        Objects.requireNonNull(getSupportActionBar()).setTitle(employee);

        editProfile = findViewById(R.id.edit_profile);
        assignActivity = findViewById(R.id.assign_activity);
        viewHistory = findViewById(R.id.view_history);

        editProfile.setOnClickListener(v -> {
            Intent toUpdateIntent = new Intent(this, UpdateEmployee.class);
            toUpdateIntent.putExtra("firstName", firstName);
            toUpdateIntent.putExtra("lastName", lastName);
            toUpdateIntent.putExtra("id", id);
            toUpdateIntent.putExtra("contact", phone);
            startActivity(toUpdateIntent);
        });

        assignActivity.setOnClickListener(v -> {
            Intent toUpdateIntent = new Intent(this, AssignActivity.class);
            toUpdateIntent.putExtra("name", firstName);
            toUpdateIntent.putExtra("name", lastName);
            toUpdateIntent.putExtra("id", id);
            toUpdateIntent.putExtra("contact", phone);
            startActivity(toUpdateIntent);
        });

        viewHistory.setOnClickListener(v -> {
            Intent toUpdateIntent = new Intent(this, ViewHistory.class);
            toUpdateIntent.putExtra("name", firstName);
            toUpdateIntent.putExtra("name", lastName);
            toUpdateIntent.putExtra("id", id);
            toUpdateIntent.putExtra("contact", phone);
            startActivity(toUpdateIntent);
        });
    }
}