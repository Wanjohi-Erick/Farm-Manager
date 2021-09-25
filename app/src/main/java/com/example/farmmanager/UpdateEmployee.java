package com.example.farmmanager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.farmmanager.databinding.ActivityUpdateEmployeeBinding;

public class UpdateEmployee extends AppCompatActivity {
    EditText name, id, contact;
    String ID, employee_name, employee_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        FloatingActionButton fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        id = findViewById(R.id.employee_id_edit);
        name = findViewById(R.id.employee_name_edit);
        contact = findViewById(R.id.employee_contact_edit);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("id");
        employee_name = bundle.getString("name");
        employee_contact = bundle.getString("contact");
        id.setText(ID);
        name.setText(employee_name);
        contact.setText(employee_contact);
    }
}