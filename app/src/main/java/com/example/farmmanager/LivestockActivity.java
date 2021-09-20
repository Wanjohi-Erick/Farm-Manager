package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.adapters.LIvestockAdapter;
import com.example.farmmanager.models.LivestockModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LivestockActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ViewSwitcher viewSwitcher;
    RecyclerView allLivestockRecycler;
    LIvestockAdapter livestockAdapter;
    List<LivestockModel> list = new ArrayList<>();
    String urlToRetrieve = "http://fmanager.agria.co.ke/retrieveAvailableLiveStock.php";
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    private static final String TAG = "ReportsFragmentBottomNav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_livestock);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewSwitcher = findViewById(R.id.view_switcher);
        allLivestockRecycler = findViewById(R.id.all_livestock_recycler);
        fab = findViewById(R.id.fab);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        //instantiating view end
        allLivestockRecycler.setHasFixedSize(true);
        allLivestockRecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        getListFromDatabase();
        fab.setOnClickListener(v -> addLivestock());
    }

    private void getListFromDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlToRetrieve, response -> {
            progressDialog.dismiss();
            try {
                JSONArray responseArray = new JSONArray(response);
                for (int i = 0; i < responseArray.length(); i++){
                    JSONObject details = responseArray.getJSONObject(i);
                    String livestockName = details.getString("name");
                    String harvestUnits = details.getString("units");
                    String landName = details.getString("land");
                    LivestockModel livestockModel = new LivestockModel(livestockName, harvestUnits, landName);
                    list.add(livestockModel);
                    livestockAdapter = new LIvestockAdapter(list);
                    allLivestockRecycler.setAdapter(livestockAdapter);
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
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void addLivestock() {
        startActivity(new Intent(this, AddLivestock.class));
    }
}