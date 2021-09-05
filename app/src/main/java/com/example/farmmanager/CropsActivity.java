package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.adapters.CropAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CropsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ViewSwitcher viewSwitcher;
    RecyclerView allCropsRecycler;
    CropAdapter cropAdapter;
    List<String> list = new ArrayList<>();
    String urlToRetrieve = "http://192.168.1.110/FarmManager/retrieveAvailableCrops.php";
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    private static final String TAG = "CropsFragmentBottomNav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crops);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewSwitcher = findViewById(R.id.view_switcher);
        allCropsRecycler = findViewById(R.id.all_crops_recycler);
        fab = findViewById(R.id.fab);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        //instantiating view end
        allCropsRecycler.setHasFixedSize(true);
        allCropsRecycler.setLayoutManager(new LinearLayoutManager(this));
        list.add("Maize");
        list.add("Beans");
        //getListFromDatabase();
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        cropAdapter = new CropAdapter(list);
        allCropsRecycler.setAdapter(cropAdapter);
        if (list.size() > 0){
            viewSwitcher.showNext();
        }
        fab.setOnClickListener(v -> addCrop());
    }

    private void getListFromDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlToRetrieve, response -> {
            progressDialog.dismiss();
            try {
                Log.d(TAG, "getListFromDatabase: " + response);
                JSONArray jsonArray = new JSONArray(response);
                int length = jsonArray.length();

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String serverResponse = jsonObject.getString("serverResponse");
                Log.d(TAG, "getListFromDatabase: "+ serverResponse);
                if (serverResponse.equalsIgnoreCase("Success")){
                    alertDialog.setTitle("title");
                    alertDialog.setMessage("Length" + length);
                    alertDialog.create();
                    alertDialog.show();
                } else {
                    alertDialog.setTitle("Server Error");
                    alertDialog.setMessage("Failed to retrieve");
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
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
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void addCrop() {
        startActivity(new Intent(this, AddCrop.class));
    }
}