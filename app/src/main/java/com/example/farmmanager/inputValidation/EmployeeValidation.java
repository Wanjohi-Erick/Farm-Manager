package com.example.farmmanager.inputValidation;

import android.text.TextUtils;
import android.widget.EditText;

public class EmployeeValidation {
    EditText employeeIDEdit, employeeFirstNameEdit, employeeLastNameEdit, employeeContactEdit;

    public EmployeeValidation(EditText employeeIDEdit, EditText employeeFirstNameEdit, EditText employeeLastNameEdit, EditText employeeContactEdit) {
        this.employeeIDEdit = employeeIDEdit;
        this.employeeFirstNameEdit = employeeFirstNameEdit;
        this.employeeLastNameEdit = employeeLastNameEdit;
        this.employeeContactEdit = employeeContactEdit;
    }

    public boolean invalidEmployeeDetails(String employeeID, String employeeFirstName, String employeeLastName, String employeeContact) {
        if (TextUtils.isEmpty(employeeID)){
            employeeIDEdit.setError("Must not be empty");
            employeeIDEdit.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(employeeFirstName)){
            employeeFirstNameEdit.setError("Must not be empty");
            employeeFirstNameEdit.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(employeeLastName)){
            employeeLastNameEdit.setError("Must not be empty");
            employeeLastNameEdit.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(employeeContact)){
            employeeContactEdit.setError("Must not be empty");
            employeeContactEdit.requestFocus();
            return true;
        }

        return false;
    }
}
