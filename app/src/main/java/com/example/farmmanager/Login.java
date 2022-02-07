package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText phoneEdit, passwordEdit;
    private final String login_url = "http://fmanager.agria.co.ke/login.php";
    private String phone, password;
    private AlertDialog.Builder dialogBuilder;
    private ProgressDialog progressDialog;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        phoneEdit = findViewById(R.id.phone_edit1);
        passwordEdit = findViewById(R.id.password_edit);
        dialogBuilder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v -> {
            phone = phoneEdit.getText().toString();
            password = passwordEdit.getText().toString();

            validateInputFields(phone, password);
            loginUser(phone, password);
            progressDialog.setTitle("Logging In");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        });
    }

    private void loginUser(String phone, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, response -> {
            progressDialog.dismiss();
            String responseType = "", registrationDate = "", firstName = "", lastName = "", emailAddress = "", phoneNumber = "", farmName = "";
            try {
                JSONArray responseArray = new JSONArray(response);
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject jsonObject = responseArray.getJSONObject(i);
                    responseType = jsonObject.getString("serverResponse");
                    registrationDate = jsonObject.getString("registrationDate");
                    firstName = jsonObject.getString("firstName");
                    lastName = jsonObject.getString("lastName");
                    emailAddress = jsonObject.getString("phone");
                    phoneNumber = jsonObject.getString("phone");
                    farmName = jsonObject.getString("farmName");
                }
                Log.i(TAG, "loginUser: " + responseType);
                if (responseType.equalsIgnoreCase("Login Successful")) {
                    Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                    intent.putExtra("regDate", registrationDate);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("phone", emailAddress);
                    intent.putExtra("phone", phoneNumber);
                    intent.putExtra("farmName", farmName);
                    startActivity(intent);
                } else {
                    dialogBuilder.setTitle("Login Failed");
                    dialogBuilder.setMessage("Check login details and try again");
                    dialogBuilder.setPositiveButton("Ok", (dialog1, which1) -> dialog1.dismiss());
                    dialogBuilder.create().show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            dialogBuilder.setTitle("Server Response");
            dialogBuilder.setMessage(error.getLocalizedMessage());
            dialogBuilder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postParams = new HashMap<>();
                postParams.put("Email", phone);
                postParams.put("Password", password);
                return postParams;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void validateInputFields(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            phoneEdit.setError("Input required");
            phoneEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Input required");
            passwordEdit.requestFocus();
        }
    }

    public void toRegistration(View view) {
        startActivity(new Intent(this, Registration.class));
        finish();
    }
}