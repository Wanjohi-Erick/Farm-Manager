package com.example.farmmanager.ui.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.Employees;
import com.example.farmmanager.Inventory;
import com.example.farmmanager.R;

public class ResourcesFragmentBottomNav extends Fragment implements View.OnClickListener {
    private TextView employees, inventory, fields, shelters, storage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //instantiating views

        View root = inflater.inflate(R.layout.fragment_resources_bottom_nav, container, false);
        employees = root.findViewById(R.id.employees);
        inventory = root.findViewById(R.id.inventory);
        fields = root.findViewById(R.id.fields);
        shelters = root.findViewById(R.id.shelters);
        storage = root.findViewById(R.id.storage);

        employees.setOnClickListener(this);
        inventory.setOnClickListener(this);
        fields.setOnClickListener(this);
        shelters.setOnClickListener(this);
        storage.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.employees){
            Intent toActivityIntent = new Intent(this.getContext(), Employees.class);
            startActivity(toActivityIntent);
        }else if (v.getId() == R.id.inventory){
            Intent intent = new Intent(this.getContext(), Inventory.class);
            startActivity(intent);
        }
    }
}