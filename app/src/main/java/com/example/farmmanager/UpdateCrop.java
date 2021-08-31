package com.example.farmmanager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UpdateCrop extends AppCompatActivity implements View.OnClickListener {

    String[] harvest_units_array, land_details_array;
    List<String> harvest_units_list = new ArrayList<>();
    List<String> land_details_list = new ArrayList<>();
    Spinner harvest_unit_spinner, land_to_plant_spinner, treatment_spinner;
    EditText cropNameEdit, yieldEdit;
    String cropNameTxt, harvest_unit, land_details, treatment, yield;
    Button updateRecordsBtn, updateTreatmentScheduleBtn;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    DatePickerDialog datePickerDialog;
    String urlToSave = "http://192.168.1.110/FarmManager/updateCropDetails.php";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //instantiating views
        updateRecordsBtn = findViewById(R.id.save_crop_btn);
        updateTreatmentScheduleBtn = findViewById(R.id.update_treatment_schedule_button);
        cropNameEdit = findViewById(R.id.crop_name);
        yieldEdit = findViewById(R.id.yield_edit);
        harvest_unit_spinner = findViewById(R.id.harvest_unit_spinner);
        land_to_plant_spinner = findViewById(R.id.land_spinner);
        treatment_spinner = findViewById(R.id.treatment_spinner);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        datePickerDialog = new DatePickerDialog(this);

        //end of instantiation
        Resources resources = getResources();
        harvest_units_array = resources.getStringArray(R.array.harvest_measurements);
        land_details_array = resources.getStringArray(R.array.land_records);
        harvest_units_list.addAll(Arrays.asList(harvest_units_array));
        land_details_list.addAll(Arrays.asList(land_details_array));
        ArrayAdapter<String> harvest_units_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, harvest_units_list);
        harvest_units_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        harvest_unit_spinner.setAdapter(harvest_units_adapter);

        ArrayAdapter<String> land_details_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, land_details_list);
        land_details_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        land_to_plant_spinner.setAdapter(land_details_adapter);
        updateRecordsBtn.setOnClickListener(this);
        updateTreatmentScheduleBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.save_crop_btn)){
            cropNameTxt = cropNameEdit.getText().toString();
            harvest_unit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    harvest_unit = String.valueOf(parent.getItemAtPosition(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    harvest_unit = "";
                }
            });
            // TODO: 25/06/2021 handle the null response from views

            land_to_plant_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    land_details = String.valueOf(parent.getItemAtPosition(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    harvest_unit = "";
                }
            });
            updateDatabase(cropNameTxt, harvest_unit, land_details, treatment, yield);
            progressDialog.setTitle("Saving crop details");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        } else if (v == findViewById(R.id.update_treatment_schedule_button)){
            //TODO Display dialog for calendarView to select treatment dates
            datePickerDialog.show();
        }
    }

    private void updateDatabase(String cropNameTxt, String unit, String land_details, String treatment, String yield) {
        InternetConnectivity internetConnectivity = new InternetConnectivity();
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No internet connection");
            alertDialog.setMessage("Please turn on wifi or mobile data");
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlToSave, response -> {
                progressDialog.dismiss();
                alertDialog.setTitle("Server Response");
                alertDialog.setMessage(response);
                feedback(response);
            }, error -> {
                progressDialog.dismiss();
                alertDialog.setMessage("Server Error");
                alertDialog.setMessage(error.getLocalizedMessage());
                feedback("Server Error");
                error.printStackTrace();
            }){
                @NotNull
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> cropDetails = new HashMap<>();
                    cropDetails.put("name", cropNameTxt);
                    cropDetails.put("units", unit);
                    cropDetails.put("land", land_details);
                    cropDetails.put("treatment", treatment);
                    cropDetails.put("yield", yield);
                    return cropDetails;
                }
            };
            stringRequest.setShouldCache(false);
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    private void feedback(String response) {
        alertDialog.setPositiveButton("Ok", (dialog, which) -> {
            if (response.trim().equalsIgnoreCase("success")){
                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UpdateCrop.this, "ERROR", Toast.LENGTH_SHORT).show();
                cropNameEdit.setText("");
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}