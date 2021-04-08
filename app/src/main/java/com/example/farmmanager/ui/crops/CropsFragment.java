package com.example.farmmanager.ui.crops;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.farmmanager.R;
import com.example.farmmanager.adapters.CropAdapter;

import java.util.ArrayList;
import java.util.List;

public class CropsFragment extends Fragment {
    List<String> list = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crops, container, false);
        RecyclerView cropsRecyclerView = view.findViewById(R.id.availableCropsRecyclerView);
        list.add("maize");
        list.add("beans");
        list.add("rice");
        list.add("wheat");
        list.add("potatoes");
        CropAdapter cropAdapter = new CropAdapter(list);
        cropsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        cropsRecyclerView.setHasFixedSize(true);
        cropsRecyclerView.setAdapter(cropAdapter);
        cropsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }
}