package com.example.farmmanager.ui.crops;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.AddCrop;
import com.example.farmmanager.R;
import com.example.farmmanager.adapters.CropAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CropsFragmentBottomNav extends Fragment {
    FloatingActionButton fab;
    ViewSwitcher viewSwitcher;
    RecyclerView allCropsRecycler;
    CropAdapter cropAdapter;
    List<String> list = new ArrayList<>();
    String urlToRetrieve = "http://192.168.1.103/FarmManager/retrieveAvailableCrops.php";
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    private static final String TAG = "CropsFragmentBottomNav";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //instantiating views

        View root = inflater.inflate(R.layout.fragment_crops_bottom_nav, container, false);
        viewSwitcher = root.findViewById(R.id.view_switcher);
        allCropsRecycler = root.findViewById(R.id.all_crops_recycler);
        fab = root.findViewById(R.id.fab);
        progressDialog = new ProgressDialog(getContext());
        alertDialog = new AlertDialog.Builder(requireContext());

        //instantiating view end
        allCropsRecycler.setHasFixedSize(true);
        allCropsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        list.add("Maize");
        list.add("Beans");
        getListFromDatabase();
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        cropAdapter = new CropAdapter(list);
        allCropsRecycler.setAdapter(cropAdapter);
        if (list.size() > 0){
            viewSwitcher.showNext();
        }
        fab.setOnClickListener(v -> addCrop());
        return root;
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
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
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
            alertDialog.setTitle("error");
            alertDialog.setMessage("error");
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void addCrop() {
        startActivity(new Intent(getContext(), AddCrop.class));
    }
}