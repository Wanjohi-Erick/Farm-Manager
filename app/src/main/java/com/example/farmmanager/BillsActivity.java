package com.example.farmmanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class BillsActivity extends AppCompatActivity {
    private Spinner billTypeSpinner;
    private EditText quantityEdit, priceEdit, transactionID;
    private RadioGroup payment_methods;
    private String[] bill_types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        billTypeSpinner = findViewById(R.id.bill_type_spinner);
        quantityEdit = findViewById(R.id.quantityInput);
        priceEdit = findViewById(R.id.priceInput);
        payment_methods = findViewById(R.id.paymentRadio);
        transactionID = findViewById(R.id.transactionIDInput);
        Resources resources = getResources();
        bill_types = resources.getStringArray(R.array.bills);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bill_types);
        billTypeSpinner.setAdapter(arrayAdapter);
    }

    public void recordReceipt(View view) {
    }

    public void recordBill(View view) {
    }
}