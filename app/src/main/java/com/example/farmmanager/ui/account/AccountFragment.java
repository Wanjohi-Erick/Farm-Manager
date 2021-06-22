package com.example.farmmanager.ui.account;

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
import com.example.farmmanager.adapters.AccountMenuAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountFragment extends Fragment {
    RecyclerView menuOptionsRecycler;
    AccountMenuAdapter accountMenuAdapter;
    List<String> menuOptions = new ArrayList<>();
    String[] list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account_bottom_nav, container, false);
        menuOptionsRecycler = root.findViewById(R.id.account_menu_options_recycler);
        Resources resources = getResources();
        list = resources.getStringArray(R.array.account_items);
        Collections.addAll(menuOptions, list);
        accountMenuAdapter = new AccountMenuAdapter(menuOptions);
        menuOptionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        menuOptionsRecycler.setHasFixedSize(true);
        menuOptionsRecycler.setAdapter(accountMenuAdapter);
        return root;
    }
}
