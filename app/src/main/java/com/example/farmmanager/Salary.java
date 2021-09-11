package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Salary extends AppCompatActivity {
    private EditText transactionIDEdit, particularsEdit, amountEdit;
    private Spinner farmActivitySpinner;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    RadioGroup paymentMethod;
    String[] farm_activities_array;
    String particulars, farm_activity, transactionID, amount;
    String url = "http://fmanager.agria.co.ke/recordSalary.php";
    private static final String TAG = "Salary";

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
        particularsEdit = findViewById(R.id.particulars_edit);
        amountEdit = findViewById(R.id.amount_edit);
        farmActivitySpinner = findViewById(R.id.farm_activity_wage_spinner);
        paymentMethod  = findViewById(R.id.paymentRadio);
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        Resources resources = getResources();
        farm_activities_array = resources.getStringArray(R.array.farm_activities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, farm_activities_array);
        farmActivitySpinner.setAdapter(arrayAdapter);

        getSpinnerItemSelected();
        getRadioItemChecked();
    }

    private void getRadioItemChecked() {
        paymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonTransfer){
                transactionIDEdit.setVisibility(View.VISIBLE);
            }else {
                transactionIDEdit.setVisibility(View.GONE);
            }
        });
    }

    private void getSpinnerItemSelected() {
        farmActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                farm_activity = farm_activities_array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                farm_activity = "";
            }
        });
    }

    public void recordSalary(View view) {
        InternetConnectivity internetConnectivity =new InternetConnectivity();
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No Internet connection");
            alertDialog.setMessage("Please turn on mobile data or connect to wifi to proceed.");
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }else {
            getFromEditTexts();
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Recording");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            sendToDatabase(particulars, farm_activity, transactionID, amount);
        }
    }

    private void sendToDatabase(String particulars, String farm_activity, String transactionID, String amount) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            progressDialog.dismiss();
            Log.d(TAG, "onResponse: " + response);
            alertDialog.setTitle("Server Response");
            alertDialog.setMessage(response);
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }, error -> {
            progressDialog.dismiss();
            Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
            alertDialog.setTitle("Server Error");
            alertDialog.setMessage(error.getLocalizedMessage());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postParams = new HashMap<>();
                postParams.put("Particulars", particulars);
                postParams.put("FarmActivity", farm_activity);
                postParams.put("Amount", amount);
                postParams.put("TransactionID", transactionID);
                return postParams;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void getFromEditTexts() {
        particulars = particularsEdit.getText().toString();
        amount = amountEdit.getText().toString();
        if (transactionIDEdit.getVisibility() == View.VISIBLE){
            transactionID = transactionIDEdit.getText().toString();
        }else transactionID = "N/A";
    }

    public void viewSalaries(View view) {
    }
}