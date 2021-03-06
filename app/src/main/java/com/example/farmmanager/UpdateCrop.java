package com.example.farmmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    String cropNameTxt, harvest_unit, land_details, activity, yield, startDate, endDate, startTime, endTime;
    Button updateRecordsBtn, updateTreatmentScheduleBtn;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    Dialog treatmentDialog;
    Dialog eventDialog;
    DatePickerDialog datePickerDialog;
    String urlToSave = "http://fmarnager.agria.co.ke/updateCropDetails.php";
    private static final String TAG = "UpdateCrop";
    CalendarView calendarView;
    String name, harvestUnits, land;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop);
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
        treatmentDialog = new Dialog(this);
        eventDialog = new Dialog(this);
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
        harvest_unit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                harvest_unit = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                harvest_unit = String.valueOf(parent.getSelectedItem());
            }
        });

        land_to_plant_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                land_details = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                harvest_unit  = String.valueOf(parent.getSelectedItem());
            }
        });
        updateRecordsBtn.setOnClickListener(this);
        updateTreatmentScheduleBtn.setOnClickListener(this);

        //From intent
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        harvestUnits  = bundle.getString("harvestUnits");
        land = bundle.getString("land");
        cropNameEdit.setText(name);
        for (int i = 0; i < harvest_units_list.size(); i++){
            for (int j = 0; j < land_details_list.size(); j++){
                if (harvest_units_list.get(i).equals(harvestUnits) && land_details_list.get(j).equals(land)){
                    harvest_unit_spinner.setSelection(i);
                    land_to_plant_spinner.setSelection(j);
                }
            }
        }

        updateTreatmentScheduleBtn.setOnClickListener(v -> {
            //TODO Display dialog for calendarView to select treatment dates
            treatmentDialog.setContentView(R.layout.date_picker_view);
            treatmentDialog.setCanceledOnTouchOutside(false);
            treatmentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            treatmentDialog.show();
            calendarView = treatmentDialog.findViewById(R.id.datePicker);
            TextView dismissBtn = treatmentDialog.findViewById(R.id.dialog_dismiss_btn);
            calendarView.setOnDateChangeListener((view, year, monthOfYear, dayOfMonth) -> {
                eventDialog.setContentView(R.layout.schedule_view);
                eventDialog.setCanceledOnTouchOutside(false);
                eventDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                eventDialog.show();
                EditText eventName = eventDialog.findViewById(R.id.activity);
                Button saveEventBtn = eventDialog.findViewById(R.id.saveEventBtn);
                TextView startDateView, endDateView, startTimeView, endTimeView;
                startDateView = eventDialog.findViewById(R.id.date_view);
                endDateView = eventDialog.findViewById(R.id.end_date_view);
                startTimeView = eventDialog.findViewById(R.id.time_view);
                endTimeView = eventDialog.findViewById(R.id.end_time_view);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d-M-Y");
                SimpleDateFormat timeFormat = new SimpleDateFormat("k:m");
                Date date = Calendar.getInstance().getTime();
                startDate = simpleDateFormat.format(date);
                endDate = simpleDateFormat.format(date);
                startTime = timeFormat.format(date);
                endTime = timeFormat.format(date);
                startDateView.setText(startDate);
                endDateView.setText(endDate);
                startTimeView.setText(startTime);
                endTimeView.setText(endTime);
                startDateView.setOnClickListener(v12 -> {
                    datePickerDialog.getDatePicker().updateDate(2020, 12, 25);
                    datePickerDialog.show();
                    datePickerDialog.setOnDateSetListener((view1, year1, month, dayOfMonth1) -> startDateView.setText(dayOfMonth1+"-"+month+"-"+year1));
                });
                saveEventBtn.setOnClickListener(v1 -> {
                    activity = eventName.getText().toString();
                    startDate = startDateView.getText().toString();
                    eventDialog.dismiss();
                });
            });
            dismissBtn.setOnClickListener(v1 -> {
                treatmentDialog.dismiss();
            });
        });
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.save_crop_btn)){
            cropNameTxt = cropNameEdit.getText().toString();
            yield = yieldEdit.getText().toString();
            updateDatabase(cropNameTxt, harvest_unit, land_details, activity, yield, startDate);
            progressDialog.setTitle("Saving crop details");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void updateDatabase(String cropNameTxt, String unit, String land_details, String activity, String yield, String startDate) {
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
                    cropDetails.put("activity", activity);
                    cropDetails.put("yield", yield);
                    cropDetails.put("treatmentDate", startDate);
                    cropDetails.put("yieldDate", endDate);
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
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_record_sale) {
            startActivity(new Intent(getApplicationContext(), RecordSalesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}