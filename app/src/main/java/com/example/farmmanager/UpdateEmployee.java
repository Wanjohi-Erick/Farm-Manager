package com.example.farmmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmployee extends AppCompatActivity {
    EditText firstNameEdit, lastNameEdit, idEdit, contactEdit, genderEdit, roleEdit, salaryEdit;
    String ID, employee_first_name, employee_last_name, employee_contact, gender, role, salary;
    Button updateEmployeeBtn;
    ProgressDialog progressDialog;
    String update_url = "http://www.fmanager.agria.co.ke/updateEmployee.php";
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        FloatingActionButton fab = findViewById(R.id.fab);
        updateEmployeeBtn = findViewById(R.id.update_employee);
        progressDialog = new ProgressDialog(this);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        idEdit = findViewById(R.id.employee_id_edit);
        firstNameEdit = findViewById(R.id.employee_first_name_edit);
        lastNameEdit = findViewById(R.id.employee_last_name_edit);
        contactEdit = findViewById(R.id.employee_contact_edit);
        genderEdit = findViewById(R.id.gender_edit);
        roleEdit = findViewById(R.id.role_edit);
        salaryEdit = findViewById(R.id.salary_edit);
        dialog = new AlertDialog.Builder(this);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("id");
        employee_first_name = bundle.getString("firstName");
        employee_last_name = bundle.getString("lastName");
        employee_contact = bundle.getString("contact");
        gender = bundle.getString("gender");
        role = bundle.getString("role");
        salary = bundle.getString("salary");
        idEdit.setText(ID);
        firstNameEdit.setText(employee_first_name);
        lastNameEdit.setText(employee_last_name);
        contactEdit.setText(employee_contact);
        if (gender.equalsIgnoreCase("null")) {
            genderEdit.setText("");
        }
        if (role.equalsIgnoreCase("null")) {
            roleEdit.setText("");
        }
        if (salary.equalsIgnoreCase("null")) {
            salaryEdit.setText("");
        }

        genderEdit.setText(gender);
        roleEdit.setText(role);
        salaryEdit.setText(salary);
        updateEmployeeBtn.setOnClickListener(v -> {
            if(validFields(gender, role, salary)) {
                gender = genderEdit.getText().toString();
                role = roleEdit.getText().toString();
                salary = salaryEdit.getText().toString();
                sendToDatabase(ID, employee_first_name, employee_last_name, employee_contact, gender, role, salary);
                progressDialog.setTitle("Recording Employee Details");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }
        });
    }

    private void sendToDatabase(String id, String firstName, String lastName, String contact, String gender, String role, String salary) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, response -> {
            progressDialog.dismiss();
            if (response.equalsIgnoreCase("success")) {
                dialog.setTitle("Server Response");
                dialog.setMessage("Employee details updated successfully");
                dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            } else {
                dialog.setTitle("Server Error");
                dialog.setMessage(response);
                dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        }, error -> {
            progressDialog.dismiss();
            String errorMessage = error.getMessage();
            dialog.setTitle("Server Error");
            if (errorMessage == null){
                dialog.setMessage("Server Error. We shall fix this as soon as possible.");
            } else {
                dialog.setMessage(errorMessage);
            }
            dialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("contact", contact);
                params.put("gender", gender);
                params.put("role", role);
                params.put("salary", salary);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private boolean validFields(String gender, String role, String salary) {
        if (TextUtils.isEmpty(gender)) {
            genderEdit.setError("Field cannot be empty");
            genderEdit.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(role)) {
            roleEdit.setError("Field cannot be empty");
            roleEdit.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(salary)) {
            salaryEdit.setError("Field cannot be empty");
            salaryEdit.requestFocus();
            return false;
        }
        return true;
    }
}