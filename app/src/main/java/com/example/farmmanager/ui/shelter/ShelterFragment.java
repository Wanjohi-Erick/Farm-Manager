package com.example.farmmanager.ui.shelter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.R;

public class ShelterFragment extends Fragment {
    private EditText location, size, condition, purpose;
    private String locationStr, sizeStr, conditionStr, purposeStr;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelter, container, false);
        location = root.findViewById(R.id.shelterLocationEdit);
        size = root.findViewById(R.id.shelterSizeEdit);
        condition = root.findViewById(R.id.shelterConditionEdit);
        purpose = root.findViewById(R.id.shelterPurposeEdit);

        getFromViews();

        return root;
    }

    private void getFromViews() {
        locationStr = location.getText().toString();
        sizeStr = size.getText().toString();
        conditionStr = condition.getText().toString();
        purposeStr = purpose.getText().toString();

    }
}