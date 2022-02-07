package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private EditText firstNameEdit, lastNameEdit, emailEdit, phoneEdit, farmNameEdit, passEdit, confirmPassEdit;
    private String firstName, lastName, email, phone, farmName, password, confirmPassword;
    private final String registration_url = "http://fmanager.agria.co.ke/registration.php";
    Button registrationBtn;
    AlertDialog.Builder dialogBuilder;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstNameEdit = findViewById(R.id.first_name_edit);
        lastNameEdit = findViewById(R.id.last_name_edit);
        emailEdit = findViewById(R.id.phone_edit1);
        phoneEdit = findViewById(R.id.phone_edit);
        farmNameEdit = findViewById(R.id.farm_name_edit);
        passEdit = findViewById(R.id.password_edit);
        confirmPassEdit = findViewById(R.id.confirm_password_edit);
        dialogBuilder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        registrationBtn = findViewById(R.id.registration_btn);
        registrationBtn.setOnClickListener(v -> {
            firstName = firstNameEdit.getText().toString();
            lastName = lastNameEdit.getText().toString();
            email = emailEdit.getText().toString();
            phone = phoneEdit.getText().toString();
            farmName = farmNameEdit.getText().toString();
            password = passEdit.getText().toString();
            confirmPassword = confirmPassEdit.getText().toString();

            validInputFields(firstName, lastName, email, phone, farmName, password, confirmPassword);
            registerUser(firstName, lastName, email, phone, farmName, password);
            progressDialog.setTitle("Registration in progress");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        });
    }

    private void registerUser(String firstName, String lastName, String email, String phone, String farmName, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registration_url, response -> {
            progressDialog.dismiss();
            dialogBuilder.setTitle("Server Response");
            dialogBuilder.setMessage(response);
            dialogBuilder.setPositiveButton("Dismiss", (dialog, which) -> {
                dialog.dismiss();
                if (response.equalsIgnoreCase("recorded successfully")) {
                    firstNameEdit.setText("");
                    lastNameEdit.setText("");
                    emailEdit.setText("");
                    phoneEdit.setText("");
                    farmNameEdit.setText("");
                    passEdit.setText("");
                    confirmPassEdit.setText("");

                    startActivity(new Intent(this, Login.class));
                    finish();
                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }, error -> {
            progressDialog.dismiss();
            dialogBuilder.setTitle("Server Response");
            dialogBuilder.setMessage(error.getLocalizedMessage());
            dialogBuilder.setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postParams = new HashMap<>();
                postParams.put("FirstName", firstName);
                postParams.put("LastName", lastName);
                postParams.put("Email", email);
                postParams.put("Phone", phone);
                postParams.put("FarmName", farmName);
                postParams.put("Password", password);
                return postParams;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void validInputFields(String firstName, String lastName, String email, String phone, String farmName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(firstName)) {
            firstNameEdit.setError("Input required");
            firstNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameEdit.setError("Input required");
            lastNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Input required");
            emailEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneEdit.setError("Input required");
            phoneEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(farmName)) {
            farmNameEdit.setError("Input required");
            farmNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passEdit.setError("Input required");
            passEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPassEdit.setError("Input required");
            confirmPassEdit.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            passEdit.setError("Passwords do not match");
            confirmPassEdit.setError("Passwords do not match");
            passEdit.requestFocus();
        }
    }

    public void toLogin(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}