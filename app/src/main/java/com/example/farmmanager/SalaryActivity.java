package com.example.farmmanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SalaryActivity extends AppCompatActivity {
    private EditText transactionID;
    private Spinner providerSpinner, farmActivitySpinner;
    RadioGroup paymentMethod;
    String[] provider_array, farm_activities_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        transactionID = findViewById(R.id.transactionIDInput);
        providerSpinner = findViewById(R.id.worker_spinner);
        farmActivitySpinner = findViewById(R.id.farm_activity_wage_spinner);
        paymentMethod  = findViewById(R.id.paymentRadio);
        Resources resources = getResources();
    }

    public void recordSalary(View view) {
    }
}