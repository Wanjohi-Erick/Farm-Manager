package com.example.farmmanager;

import android.content.res.Resources;
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
import java.util.Arrays;
import java.util.List;

public class Inventory extends AppCompatActivity {
    RecyclerView inventoryRecycler;
    List<String> inventoryCategories = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Resources resources = getResources();
        String[] cats;
        cats = resources.getStringArray(R.array.inventory_item_categories);
        inventoryCategories.addAll(Arrays.asList(cats));
        inventoryRecycler = findViewById(R.id.inventory_recycler);
        inventoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        inventoryRecycler.setHasFixedSize(false);
        InventoryAdapter inventoryAdapter = new InventoryAdapter(inventoryCategories);
        inventoryRecycler.setAdapter(inventoryAdapter);
    }
}