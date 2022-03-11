package com.example.farmmanager.ui.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.Employees;
import com.example.farmmanager.Inventory;
import com.example.farmmanager.LauncherActivity;
import com.example.farmmanager.R;

public class ResourcesFragmentBottomNav extends Fragment {
    private TextView employees, inventory, fields, shelters, storage;
    String details;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //instantiating views

        View root = inflater.inflate(R.layout.fragment_resources_bottom_nav, container, false);
        employees = root.findViewById(R.id.employees);
        inventory = root.findViewById(R.id.inventory);
        fields = root.findViewById(R.id.fields);
        shelters = root.findViewById(R.id.shelters);
        storage = root.findViewById(R.id.storage);

        Bundle bundle = getActivity().getIntent().getExtras();
        String userName = bundle.getString("firstName");
        String farmName = bundle.getString("farmName");
        details = String.format("%s@%s", userName, farmName);

        employees.setOnClickListener(v -> {
            Intent toActivityIntent = new Intent(this.getContext(), Employees.class);
            toActivityIntent.putExtra("userDetails", details);
            startActivity(toActivityIntent);
        });

        return root;
    }
}