package com.example.farmmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.adapters.InventoryAdapter;
import com.example.farmmanager.models.InventoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {
    RecyclerView inventoryRecycler;
    List<InventoryModel> inventoryCategories = new ArrayList<InventoryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        inventoryRecycler = findViewById(R.id.inventory_recycler);
        inventoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        inventoryRecycler.setHasFixedSize(false);
        InventoryAdapter inventoryAdapter = new InventoryAdapter(inventoryCategories);
        inventoryRecycler.setAdapter(inventoryAdapter);
    }
}