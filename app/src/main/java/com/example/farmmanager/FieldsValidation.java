package com.example.farmmanager;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

public class FieldsValidation {
    private final EditText particulars, commodity, quantity;
    private Context context;

    public FieldsValidation (Context context, EditText particulars, EditText commodity, EditText quantity) {
        this.context = context;
        this.particulars = particulars;
        this.commodity = commodity;
        this.quantity = quantity;
    }

    public boolean invalidInputFields(String particularsStr, String commodityStr, String quantityStr){
        if (TextUtils.isEmpty(particularsStr)){
            particulars.setError("Input required!");
            particulars.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(commodityStr)){
            commodity.setError("Input required!");
            commodity.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(quantityStr)){
            quantity.setError("Input required!");
            quantity.requestFocus();
            return true;
        }
        return false;
    }
}
