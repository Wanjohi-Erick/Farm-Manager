package com.example.farmmanager.ui.revenue;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.adapters.IncomeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IncomeFragment extends Fragment {
    RecyclerView incomeRecycler;
    IncomeAdapter incomeAdapter;
    List<String> incomeCategories = new ArrayList<>();
    String [] incomeAsArray;
    Resources resources;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        incomeRecycler = view.findViewById(R.id.income_recycler);
        incomeRecycler.setHasFixedSize(true);
        incomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        resources = getResources();
        incomeAsArray = resources.getStringArray(R.array.income_categories);
        incomeCategories.addAll(Arrays.asList(incomeAsArray));
        incomeAdapter = new IncomeAdapter(incomeCategories);

        incomeRecycler.setAdapter(incomeAdapter);
        return view;
    }
}
