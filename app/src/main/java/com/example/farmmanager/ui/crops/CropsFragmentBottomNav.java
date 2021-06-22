package com.example.farmmanager.ui.crops;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.AddCrop;
import com.example.farmmanager.MainActivity;
import com.example.farmmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CropsFragmentBottomNav extends Fragment {
    FloatingActionButton fab;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crops_bottom_nav, container, false);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Customize", Snackbar.LENGTH_SHORT).setAction("Action", addCrop()).show();
            }
        });
        return root;
    }

    private View.OnClickListener addCrop() {
        startActivity(new Intent(getContext(), AddCrop.class));
        return null;
    }
}