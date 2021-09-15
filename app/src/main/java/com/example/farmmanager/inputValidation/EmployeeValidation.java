package com.example.farmmanager.inputValidation;

import android.text.TextUtils;
import android.widget.EditText;

public class EmployeeValidation {
    EditText employeeIDEdit, employeeNameEdit, employeeContactEdit;

    public EmployeeValidation(EditText employeeIDEdit, EditText employeeNameEdit, EditText employeeContactEdit) {
        this.employeeIDEdit = employeeIDEdit;
        this.employeeNameEdit = employeeNameEdit;
        this.employeeContactEdit = employeeContactEdit;
    }

    public boolean invalidEmployeeDetails(String employeeID, String employeeName, String employeeContact) {
        if (TextUtils.isEmpty(employeeID)){
            employeeIDEdit.setError("Must not be empty");
            employeeIDEdit.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(employeeName)){
            employeeNameEdit.setError("Must not be empty");
            employeeNameEdit.requestFocus();
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
