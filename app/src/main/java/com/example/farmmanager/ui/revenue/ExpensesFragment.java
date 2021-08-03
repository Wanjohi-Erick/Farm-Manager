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
import com.example.farmmanager.adapters.ExpensesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpensesFragment extends Fragment {
    RecyclerView expensesRecycler;
    ExpensesAdapter expensesAdapter;
    List<String> expensesCategories = new ArrayList<>();
    String[] expensesAsList;
    Resources resources;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        expensesRecycler = view.findViewById(R.id.expenses_recycler);
        expensesRecycler.setHasFixedSize(true);
        expensesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        resources = getResources();
        expensesAsList = resources.getStringArray(R.array.expenses_categories);
        expensesCategories.addAll(Arrays.asList(expensesAsList));
        expensesAdapter = new ExpensesAdapter(expensesCategories);

        expensesRecycler.setAdapter(expensesAdapter);
        return view;
    }
}
