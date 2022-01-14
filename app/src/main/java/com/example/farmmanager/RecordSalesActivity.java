package com.example.farmmanager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecordSalesActivity extends AppCompatActivity {
    private EditText particularsEdit, quantityEdit, priceEdit, transactionIDEdit, contactEdit;
    private String particulars, commodity, quantity, price, contact, transactionID, captureImageInfo;
    private RadioGroup paymentRadio;
    private Spinner commoditySpinner;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private static final String TAG = "VolleyTag";
    private final String recordSalesUrl = "http://fmanager.agria.co.ke/recordSale.php";
    private String[] sale_items;
    private final int requestCode = 100;
    private Bitmap captureImage;


    InternetConnectivity internetConnectivity = new InternetConnectivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sales);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPermissions();

        FloatingActionButton fab = findViewById(R.id.fab);
        particularsEdit = findViewById(R.id.particularsInput);
        commoditySpinner = findViewById(R.id.commodityInput);
        quantityEdit = findViewById(R.id.quantityInput);
        alertDialog = new AlertDialog.Builder(this);
        paymentRadio = findViewById(R.id.paymentRadio);
        priceEdit = findViewById(R.id.priceInput);
        transactionIDEdit = findViewById(R.id.transactionIDInput);
        contactEdit = findViewById(R.id.contactInput);
        Resources resources = getResources();
        sale_items = resources.getStringArray(R.array.sale_items);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sale_items);
        commoditySpinner.setAdapter(arrayAdapter);
        getSpinnerItemSelected();
        getRadioChecked();
    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA
            }, requestCode);
        }
    }

    private void getSpinnerItemSelected() {
        commoditySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                commodity = sale_items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                commodity = "";
            }
        });
    }

    private void getRadioChecked() {
        paymentRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonTransfer){
                transactionIDEdit.setVisibility(View.VISIBLE);
            }else {
                transactionIDEdit.setVisibility(View.GONE);
            }
        });
    }

    private void getFromEditTexts() {
        particulars = particularsEdit.getText().toString();
        //Commodity taken from getSpinnerItemSelected();
        quantity = quantityEdit.getText().toString();
        price = priceEdit.getText().toString();
        contact = contactEdit.getText().toString();
        if (transactionIDEdit.getVisibility() == View.VISIBLE){
            transactionID = transactionIDEdit.getText().toString();
        }else {
            transactionID = "N/A";
        }
    }

    private void sendToDatabase(String particulars, String commodity, String quantity) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, recordSalesUrl, response -> {
            progressDialog.dismiss();
            alertDialog.setTitle("Server Response");
            alertDialog.setMessage(response);
            AlertDialog dialog = alertDialog.create();
            dialog.show();
            feedback(response);
        }, error -> {
            progressDialog.dismiss();
            alertDialog.setTitle("Server not Found");
            Log.d(TAG, "sendToDatabase: "+ error.getLocalizedMessage());
            alertDialog.setMessage(error.getLocalizedMessage());
            AlertDialog dialog = alertDialog.create();
            dialog.show();
            feedback("server-error");
            error.printStackTrace();
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("Particulars", particulars);
                hashMap.put("Commodity", commodity);
                hashMap.put("Quantity", quantity);
                hashMap.put("Price", price);
                hashMap.put("TransactionID", transactionID);
                hashMap.put("Contact", contact);
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
        if (id == R.id.action_record_sale) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void recordSales(View view) {
        if (!internetConnectivity.isConnected(this)){
            alertDialog.setTitle("No Internet connection");
            alertDialog.setMessage("Please turn on mobile data or connect to wifi to proceed.");
            feedback("internet-error");
        }else {
            getFromEditTexts();
            FieldsValidation fieldsValidation = new FieldsValidation(this, particularsEdit, quantityEdit, priceEdit, transactionIDEdit, contactEdit);
            //TODO Fields validation for receipt information to be edited
            if (fieldsValidation.invalidInputFields(particulars, quantity, price, transactionID, contact, captureImageInfo)) return;
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
                emptyEditTexts();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void emptyEditTexts() {
        particularsEdit.setText("");
        quantityEdit.setText("");
        commoditySpinner.setSelection(0);
        priceEdit.setText("");
        transactionIDEdit.setText("");
        contactEdit.setText("");
    }

    public void recordReceipt(View view) {
        //TODO Create an event handler for capturing receipt information
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            captureImage = (Bitmap) data.getExtras().get("data");
            Date date = new Date();
            getFromEditTexts();
            captureImageInfo = "receipt_" + particulars + "_" + price + "_" + date;
            Toast.makeText(this, captureImageInfo, Toast.LENGTH_SHORT).show();
        }
    }
}