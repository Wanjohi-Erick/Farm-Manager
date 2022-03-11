package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.adapters.EmployeesAdapter;
import com.example.farmmanager.models.EmployeesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employees extends AppCompatActivity {
    private RecyclerView employeesRecycler;
    private final List<EmployeesModel> employeeList = new ArrayList<>();
    EmployeesAdapter employeesAdapter;
    FloatingActionButton fab;
    ViewSwitcher viewSwitcher;
    String urlToRetrieve = "http://fmanager.agria.co.ke/retrieveEmployeeDetails.php";
    String username, farmName, details;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employees);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        details = bundle.getString("userDetails");

        viewSwitcher = findViewById(R.id.view_switcher);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddEmployee.class).putExtra("userDetails", details)));
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        employeesRecycler = findViewById(R.id.employeeRecycler);
        employeesRecycler.setHasFixedSize(false);
        employeesRecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        getFromDatabase();
    }

    private void getFromDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlToRetrieve, response -> {
            progressDialog.dismiss();
            try {
                JSONArray responseArray = new JSONArray(response);
                for (int i = 0; i < responseArray.length(); i++){
                    JSONObject details = responseArray.getJSONObject(i);
                    String employeeFirstName = details.getString("firstName");
                    String employeeLastName = details.getString("lastName");
                    String employeeID = details.getString("employeeID");
                    String employeeContact = details.getString("contact");
                    String dateOfEmployment = details.getString("dateOfEmployment");
                    EmployeesModel employeesModel = new EmployeesModel(employeeID, employeeFirstName, employeeLastName, employeeContact, dateOfEmployment);
                    employeeList.add(employeesModel);
                    employeesAdapter = new EmployeesAdapter(employeeList);
                    employeesRecycler.setAdapter(employeesAdapter);
                }
                if (employeeList.size() > 0){
                    viewSwitcher.showNext();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            String message = error.getMessage();
            alertDialog.setTitle("Server Error");
            if (message == null){
                alertDialog.setMessage("Error from our side. Hold tight as we fix it");
            }else {
                alertDialog.setMessage(error.getLocalizedMessage());
            }
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userName", details);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }
}