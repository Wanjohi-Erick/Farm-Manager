package com.example.farmmanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.inputValidation.EmployeeValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class AddEmployee extends AppCompatActivity implements View.OnClickListener {
    //todo Add internet connectivity check to all activities and display a text box with internet error
    private EditText employeeFirstNameEdit, employeeLastNameEdit, employeeIDEdit, employeeContactEdit;
    private Button saveEmployeeBtn;
    private final String recordEmployeeUrl = "http://fmanager.agria.co.ke/recordEmployeeDetails.php";
    private static final String TAG = "AddEmployee";
    private ProgressDialog progressDialog;
    private AlertDialog.Builder dialog;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_employee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        employeeIDEdit = findViewById(R.id.employee_id);
        employeeFirstNameEdit = findViewById(R.id.employee_first_name);
        employeeLastNameEdit = findViewById(R.id.employee_last_name);
        employeeContactEdit = findViewById(R.id.employee_contact);
        saveEmployeeBtn = findViewById(R.id.save_employee_btn);
        progressDialog = new ProgressDialog(this);
        dialog = new AlertDialog.Builder(this);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        saveEmployeeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String employeeFirstName, employeeLastName, employeeID, employeeContact;
        if (v == saveEmployeeBtn){
            Bundle bundle = getIntent().getExtras();
            userName = bundle.getString("userDetails");
            employeeID = employeeIDEdit.getText().toString();
            employeeFirstName = employeeFirstNameEdit.getText().toString();
            employeeLastName = employeeLastNameEdit.getText().toString();
            employeeContact = employeeContactEdit.getText().toString();

            EmployeeValidation employeeValidation = new EmployeeValidation(employeeIDEdit, employeeFirstNameEdit, employeeLastNameEdit, employeeContactEdit);
            if (employeeValidation.invalidEmployeeDetails(employeeID, employeeFirstName, employeeLastName, employeeContact))return;
            sendToDatabase(employeeID, employeeFirstName, employeeLastName, employeeContact, userName);
            progressDialog.setTitle("Recording Employee Details");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void sendToDatabase(String employeeID, String employeeFirstName, String employeeLastName, String employeeContact, String userName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, recordEmployeeUrl, response -> {
            progressDialog.dismiss();
            Log.d(TAG, "onResponse: " + response);
            if (response.equalsIgnoreCase("recorded successfully")) {
                dialog.setTitle("Server Response");
                dialog.setMessage("Employee details recorded successfully");
                dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                employeeIDEdit.setText("");
                employeeLastNameEdit.setText("");
                employeeContactEdit.setText("");
            } else {
                dialog.setTitle("Server Error");
                dialog.setMessage(response);
                dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        }, error -> {
            progressDialog.dismiss();
            String errorMessage = error.getMessage();
            dialog.setTitle("Server Error");
            if (errorMessage == null){
                dialog.setMessage("Server Error. We shall fix this as soon as possible.");
            } else {
                dialog.setMessage(errorMessage);
            }
            dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("employeeID", employeeID);
                params.put("employeeFirstName", employeeFirstName);
                params.put("employeeLastName", employeeLastName);
                params.put("employeeContact", employeeContact);
                params.put("userName", userName);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}