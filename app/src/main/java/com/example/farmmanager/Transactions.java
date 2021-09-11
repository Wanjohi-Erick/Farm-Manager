package com.example.farmmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.adapters.TransactionsHistoryAdapter;
import com.example.farmmanager.models.TransactionsList;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    private GraphView graphView;
    RecyclerView transactionsRecycler;
    TransactionsHistoryAdapter transactionsHistoryAdapter;
    private String get_transactions_from_db_url = "http://fmanager.agria.co.ke/retrieveSales.php";
    private static final String TAG = "Transactions";
    List<TransactionsList> transactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transactions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        graphView = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 0),
                new DataPoint(6, 300),
                new DataPoint(11, 1300),
                new DataPoint(19, 1500),
                new DataPoint(20, 3060),
                new DataPoint(25, 3605)
        });
        graphView.addSeries(series);
        graphView.setTitle("August summary");

        transactionsRecycler = findViewById(R.id.transactions_recycler);
        transactionsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        transactionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        transactionsRecycler.setHasFixedSize(false);
        getTransactionDetails();
    }

    private void getTransactionDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_transactions_from_db_url, response -> {
            String formattedResponse = "";
            try {
                JSONArray transactionsDetails = new JSONArray(response);
                for (int i = 0; i <= transactionsDetails.length(); i++){
                    JSONObject jsonObject = transactionsDetails.getJSONObject(i);
                    String date = jsonObject.getString("date");
                    String particulars = jsonObject.getString("particulars");
                    String commodity = jsonObject.getString("commodity");
                    String quantity = jsonObject.getString("quantity");
                    String price = jsonObject.getString("price");
                    String transactionID = jsonObject.getString("transactionID");
                    String contact = jsonObject.getString("contact");

                    formattedResponse = date + ", " + particulars + ", " + commodity + ", " + quantity + ", " + price + ", " + transactionID + ", " + contact;
                    Log.d(TAG, "getTransactionDetails: " + formattedResponse);
                    TransactionsList transactionsList = new TransactionsList(date, particulars, commodity, quantity, price, transactionID, contact);
                    transactions.add(transactionsList);
                    transactionsHistoryAdapter = new TransactionsHistoryAdapter(transactions);
                    transactionsRecycler.setAdapter(transactionsHistoryAdapter);
                    Log.d(TAG, "onCreate: " + transactions.size());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(Transactions.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}