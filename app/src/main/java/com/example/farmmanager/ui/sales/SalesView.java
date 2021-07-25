package com.example.farmmanager.ui.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.R;
import com.example.farmmanager.adapters.SalesViewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalesView extends Fragment {

    String fetchRecordsUrl = "http://10.0.2.2/FarmManager/retrieveSales.php";
    RecyclerView salesRecycler;
    SalesViewRecyclerAdapter salesViewRecyclerAdapter;
    List<String> records = new ArrayList<>();
    private static final String TAG = "SalesView";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        salesRecycler = view.findViewById(R.id.recorded_sales_recycler);
        records.add("Record 1");
        records.add("record 2");
        getRecordsFromDatabase();
        salesRecycler.setHasFixedSize(true);
        salesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        salesViewRecyclerAdapter = new SalesViewRecyclerAdapter(records);
        salesRecycler.setAdapter(salesViewRecyclerAdapter);

        return view;
    }

    private void getRecordsFromDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, fetchRecordsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+ error.getLocalizedMessage()  );
            }
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }
}
