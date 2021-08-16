package com.example.farmmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

public class FieldsValidation {
    private final EditText particulars, quantity, price, transactionID, contact;
    private Context context;

    public FieldsValidation (Context context, EditText particulars, EditText quantity, EditText price, EditText transactionID, EditText contact) {
        this.context = context;
        this.particulars = particulars;
        this.quantity = quantity;
        this.price = price;
        this.transactionID = transactionID;
        this.contact = contact;
    }

    public boolean invalidInputFields(String particularsStr, String quantityStr, String priceStr, String transactionIDStr, String contactStr, String captureImageInfo){
        if (TextUtils.isEmpty(particularsStr)){
            particulars.setError("Input required!");
            particulars.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(quantityStr)){
            quantity.setError("Input required!");
            quantity.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(priceStr)){
            price.setError("Input required!");
            price.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(transactionIDStr) && transactionID.getVisibility() == View.VISIBLE){
            transactionID.setError("Input required!");
            transactionID.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(contactStr)){
            contact.setError("Input required!");
            contact.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(captureImageInfo)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("RECEIPT NOT RECORDED");
            builder.setMessage("Please record the receipt issued");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return false;
    }

    public boolean invalidInputFields(String transactionIDStr) {
        if (TextUtils.isEmpty(transactionIDStr)){
            transactionID.setError("Input Required");
            transactionID.requestFocus();
            return true;
        }
        return false;
    }
}
