package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCrop extends AppCompatActivity implements View.OnClickListener {
    String[] items;
    List<String> list = new ArrayList<>();
    Spinner spinner;
    EditText cropName;
    String cropNameTxt, unit;
    Button save;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    String urlToSave = "http://192.168.1.103/FarmManager/recordCrops.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //instantiating views
        save = findViewById(R.id.save_crop_btn);
        cropName = findViewById(R.id.crop_name);
        spinner = findViewById(R.id.harvest_unit_spinner);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        //end of instantiation
        Resources resources = getResources();
        items = resources.getStringArray(R.array.harvest_measurements);
        list.addAll(Arrays.asList(items));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.save_crop_btn)){
            cropNameTxt = cropName.getText().toString();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unit = String.valueOf(parent.getItemAtPosition(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    unit = "";
                }
            });
            // TODO: 25/06/2021 handle the null response from views

            sendToDatabase(cropNameTxt, unit);
            progressDialog.setTitle("Saving crop details");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void sendToDatabase(String cropNameTxt, String unit) {
        InternetConnectivity internetConnectivity = new InternetConnectivity();
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No internet connection");
            alertDialog.setMessage("Please turn on wifi or mobile data");
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlToSave, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(response);
                    feedback(response);
                }
            }, error -> {
                progressDialog.dismiss();
                alertDialog.setMessage("Server Error");
                alertDialog.setMessage(error.getLocalizedMessage());
                feedback("Server Error");
                error.printStackTrace();
            }){
                @Nullable
                @org.jetbrains.annotations.Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> cropDetails = new HashMap<>();
                    cropDetails.put("name", cropNameTxt);
                    cropDetails.put("units", unit);
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
                Toast.makeText(AddCrop.this, "ERROR", Toast.LENGTH_SHORT).show();
                cropName.setText("");
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}