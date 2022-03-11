package com.example.farmmanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class UpdateEmployee extends AppCompatActivity {
    EditText name, id, contact;
    Spinner activitySpinner;
    String ID, employee_name, employee_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        id = findViewById(R.id.employee_id_edit);
        name = findViewById(R.id.employee_name_edit);
        contact = findViewById(R.id.employee_contact_edit);
        activitySpinner = findViewById(R.id.employee_task_spinner);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("id");
        employee_name = bundle.getString("name");
        employee_contact = bundle.getString("contact");
        id.setText(ID);
        name.setText(employee_name);
        contact.setText(employee_contact);

        Resources resources = getResources();
        String[] activities = resources.getStringArray(R.array.farm_activities);
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activities);
        activitySpinner.setAdapter(adapter);
    }
}