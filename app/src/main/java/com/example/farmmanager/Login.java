package com.example.farmmanager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Set;

public class Login extends AppCompatActivity {
    private EditText phoneEdit, passwordEdit, phoneEditReset, passEditReset, confirmPassEdit;
    private final String login_url = "http://fmanager.agria.co.ke/login.php";
    private final String reset_pass_url = "http://fmanager.agria.co.ke/resetPass.php";
    private String phone, password;
    private AlertDialog.Builder dialogBuilder;
    private ProgressDialog progressDialog;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dialogBuilder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);

        String userId, userPass;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
        userId = prefs.getString("userId", "user");
        userPass = prefs.getString("userPass", "pass");

        if (!userId.equalsIgnoreCase("user") && !userPass.equalsIgnoreCase("pass")) {
            /* progressDialog.setTitle("Logging in");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

             */
            loginUser(userId, userPass);
        }

        TextView resetPassword;
        phoneEdit = findViewById(R.id.phone_edit1);
        passwordEdit = findViewById(R.id.password_edit);
        resetPassword = findViewById(R.id.reset_password);
        resetPassword.setOnClickListener(v -> resetPassword());
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v -> {
            phone = phoneEdit.getText().toString();
            password = passwordEdit.getText().toString();

            if (validateInputFields(phone, password)) {
                loginUser(phone, password);
                progressDialog.setTitle("Logging In");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }
        });
    }

    private void resetPassword() {
        Dialog resetPassDialog = new Dialog(this);
        resetPassDialog.setContentView(R.layout.reset_pass_layout);
        Button resetPassBtn, cancelBtn;
        phoneEditReset = resetPassDialog.findViewById(R.id.phone_edit_reset);
        passEditReset = resetPassDialog.findViewById(R.id.password_edit_reset);
        confirmPassEdit = resetPassDialog.findViewById(R.id.confirm_password_edit_reset);
        cancelBtn = resetPassDialog.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> resetPassDialog.dismiss());
        resetPassBtn = resetPassDialog.findViewById(R.id.reset_pass_btn);
        resetPassBtn.setOnClickListener(v -> {
            String phone, pass, conPass;
            phone = phoneEditReset.getText().toString();
            pass = passEditReset.getText().toString();
            conPass = confirmPassEdit.getText().toString();

            if (validFields(phone, pass, conPass)) {
                Toast.makeText(this, "valid", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "resetPassword: valid");
                sendToDatabase(phone, pass, resetPassDialog);
                progressDialog.setTitle("Resetting Password");
                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
            }
        });
        resetPassDialog.show();
    }

    private void sendToDatabase(String phone, String pass, Dialog resetPassDialog) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, reset_pass_url, response -> {
            if (response.equalsIgnoreCase("success")) {
                progressDialog.dismiss();
                dialogBuilder.setTitle("Server Response");
                dialogBuilder.setMessage(response);
                dialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                    resetPassDialog.dismiss();
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            } else if (response.equalsIgnoreCase("null")) {
                progressDialog.dismiss();
                dialogBuilder.setTitle("Error");
                dialogBuilder.setMessage("The provided phone number does not exist in our system.");
                dialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                    resetPassDialog.dismiss();
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            } else {
                progressDialog.dismiss();
                dialogBuilder.setTitle("Server Response");
                dialogBuilder.setMessage(response);
                dialogBuilder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
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
                Map<String, String> params = new HashMap<>();
                params.put("Phone", phone);
                params.put("Password", pass);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private boolean validFields(String phone, String pass, String conPass) {
        if (TextUtils.isEmpty(phone)) {
            phoneEditReset.setError("Input Required!");
            phoneEditReset.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(pass)) {
            passEditReset.setError("Input Required!");
            passEditReset.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(conPass)) {
            confirmPassEdit.setError("Input Required!");
            confirmPassEdit.requestFocus();
            return false;
        }

        if (!pass.equalsIgnoreCase(conPass)) {
            passEditReset.setError("Passwords do not match!");
            confirmPassEdit.setError("Passwords do not match!");
            passEditReset.requestFocus();
            return false;
        }
        return true;
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

                    //auto login
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);

                    prefs.edit().putString("userId",phone).apply();
                    prefs.edit().putString("userPass", password).apply();
                    startActivity(intent);
                } else {
                    dialogBuilder.setTitle("Login Failed");
                    dialogBuilder.setMessage("Check login details and try again");
                    dialogBuilder.setPositiveButton("Ok", (dialog1, which1) -> dialog1.dismiss());
                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
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

    private boolean validateInputFields(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            phoneEdit.setError("Input required");
            phoneEdit.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Input required");
            passwordEdit.requestFocus();
            return false;
        }
        return true;
    }

    public void toRegistration(View view) {
        startActivity(new Intent(this, Registration.class));
        finish();
    }
}