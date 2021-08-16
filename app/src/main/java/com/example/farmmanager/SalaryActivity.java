package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SalaryActivity extends AppCompatActivity {
    private EditText transactionIDEdit;
    private Spinner providerSpinner, farmActivitySpinner;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    RadioGroup paymentMethod;
    String[] provider_array, farm_activities_array;
    String particulars, farm_activity, transactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        transactionIDEdit = findViewById(R.id.transactionIDInput);
        providerSpinner = findViewById(R.id.worker_spinner);
        farmActivitySpinner = findViewById(R.id.farm_activity_wage_spinner);
        paymentMethod  = findViewById(R.id.paymentRadio);
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        Resources resources = getResources();
    }

    public void recordSalary(View view) {
        InternetConnectivity internetConnectivity =new InternetConnectivity();
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No Internet connection");
            alertDialog.setMessage("Please turn on mobile data or connect to wifi to proceed.");
            feedback("internet-error");
        }else {
            getFromEditTexts();
            //TODO Fields validation for receipt information to be edited
            sendToDatabase(particulars, farm_activity, transactionID);
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Recording");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }
    }

    private void sendToDatabase(String particulars, String farm_activity, String transactionID) {

    }

    private void getFromEditTexts() {

    }

    private void feedback(String feedback) {

    }
}