package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.farmmanager.adapters.CropAdapter;
import com.example.farmmanager.models.CropsModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ViewSwitcher viewSwitcher;
    RecyclerView allCropsRecycler;
    CropAdapter cropAdapter;
    List<CropsModel> list = new ArrayList<>();
    String urlToRetrieve = "http://192.168.2.124/FarmManager/retrieveAvailableCrops.php";
    String username, farmName, details;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crops);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        farmName = bundle.getString("farmName");
        details = String.format("%s@%s", username, farmName);
        MobileAds.initialize(this, initializationStatus -> {

        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        viewSwitcher = findViewById(R.id.view_switcher);
        allCropsRecycler = findViewById(R.id.all_crops_recycler);
        fab = findViewById(R.id.fab);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        //instantiating view end
        allCropsRecycler.setHasFixedSize(true);
        allCropsRecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        getListFromDatabase();
        fab.setOnClickListener(v -> addCrop());
    }

    private void getListFromDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlToRetrieve, response -> {
            progressDialog.dismiss();
            try {
                JSONArray responseArray = new JSONArray(response);
                for (int i = 0; i < responseArray.length(); i++){
                    JSONObject details = responseArray.getJSONObject(i);
                    String cropName = details.getString("name");
                    String harvestUnits = details.getString("units");
                    String landName = details.getString("land");
                    CropsModel cropsModel = new CropsModel(cropName, harvestUnits, landName);
                    list.add(cropsModel);
                    cropAdapter = new CropAdapter(list);
                    allCropsRecycler.setAdapter(cropAdapter);
                }
                if (list.size() > 0){
                    viewSwitcher.showNext();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            alertDialog.setTitle("Server Error");
            alertDialog.setMessage(error.getLocalizedMessage());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserName", details);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void addCrop() {
        Intent intent = new Intent(this, AddCrop.class);
        intent.putExtra("userDetails", details);
        startActivity(intent);
    }
}