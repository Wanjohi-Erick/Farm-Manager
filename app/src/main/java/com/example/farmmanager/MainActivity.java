package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText particularsEdit, commodityEdit, quantityEdit;
    private String particulars, commodity, quantity;
    private final String recordSalesUrl = "http://10.0.2.2/FarmManager/recordSale.php";
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private static final String TAG = "VolleyTag";

    InternetConnectivity internetConnectivity = new InternetConnectivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        particularsEdit = findViewById(R.id.particularsInput);
        commodityEdit = findViewById(R.id.commodityInput);
        quantityEdit = findViewById(R.id.quantityInput);
        alertDialog = new AlertDialog.Builder(this);
    }

    private void getFromEditTexts() {
        particulars = particularsEdit.getText().toString();
        commodity = commodityEdit.getText().toString();
        quantity = quantityEdit.getText().toString();
    }

    private void sendToDatabase(String particulars, String commodity, String quantity) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, recordSalesUrl, response -> {
            progressDialog.dismiss();
            alertDialog.setTitle("Server Response");
            alertDialog.setMessage(response);
            feedback(response);
        }, error -> {
            progressDialog.dismiss();
            alertDialog.setTitle("Server not Found");
            Log.d(TAG, "sendToDatabase: "+ error.getLocalizedMessage());
            alertDialog.setMessage(error.getLocalizedMessage());
            feedback("server-error");
            error.printStackTrace();
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("Particulars", particulars);
                hashMap.put("Commodity", commodity);
                hashMap.put("Quantity", quantity);
                return hashMap;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
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
        if (id == R.id.action_back) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void recordSales(View view) {
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No Internet connection");
            alertDialog.setMessage("Please turn on mobile data or connect to wifi to proceed.");
            feedback("internet-error");
        }else {
            getFromEditTexts();
            FieldsValidation fieldsValidation = new FieldsValidation(this, particularsEdit, commodityEdit, quantityEdit);
            if (fieldsValidation.invalidInputFields(particulars, commodity, quantity)) return;
            sendToDatabase(particulars, commodity, quantity);
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Recording");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }
    }

    private void feedback(final String response){
        alertDialog.setPositiveButton("Ok", (dialog, which) -> {
            if (response.trim().equalsIgnoreCase("recorded successfully")){
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
            if (response.trim().equalsIgnoreCase("recording failed")){
                particularsEdit.setText("");
                commodityEdit.setText("");
                quantityEdit.setText("");
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

}