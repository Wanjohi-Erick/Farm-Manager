package com.example.farmmanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCrop extends AppCompatActivity {
    String[] items;
    List<String> list = new ArrayList<>();
    Spinner spinner;
    EditText cropName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cropName = findViewById(R.id.crop_name);
        spinner = findViewById(R.id.harvest_unit_spinner);
        Resources resources = getResources();
        items = resources.getStringArray(R.array.harvest_measurements);
        list.addAll(Arrays.asList(items));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        String cropNameTxt, unit;
        cropNameTxt = cropName.getText().toString();
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}