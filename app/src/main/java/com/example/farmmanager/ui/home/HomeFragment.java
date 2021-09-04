package com.example.farmmanager.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.farmmanager.CropsActivity;
import com.example.farmmanager.LivestockActivity;
import com.example.farmmanager.R;
import com.example.farmmanager.Transactions;
import com.example.farmmanager.ui.revenue.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment implements View.OnClickListener {
    CardView transactionsCardView, livestockCardView, cropsCardView, notificationsCardView, calendarCardView, settingsCardView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_bottom_nav, container,false);
        transactionsCardView = root.findViewById(R.id.transactions_card_view);
        livestockCardView = root.findViewById(R.id.livestock_card_view);
        cropsCardView = root.findViewById(R.id.crops_card_view);
        notificationsCardView = root.findViewById(R.id.notifications_card_view);
        calendarCardView = root.findViewById(R.id.calendar_card_view);
        settingsCardView = root.findViewById(R.id.settings_card_view);
        transactionsCardView.setOnClickListener(this);
        livestockCardView.setOnClickListener(this);
        cropsCardView.setOnClickListener(this);
        notificationsCardView.setOnClickListener(this);
        calendarCardView.setOnClickListener(this);
        settingsCardView.setOnClickListener(this);

        com.example.farmmanager.ui.home.SectionsPagerAdapter sectionsPagerAdapter = new com.example.farmmanager.ui.home.SectionsPagerAdapter(getContext(), getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pagerWeatherNews);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabsWeatherNews);
        tabs.setupWithViewPager(viewPager);
        return root;
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        switch (ID){
            case R.id.transactions_card_view:
                startActivity(new Intent(getContext(), Transactions.class));
                break;
            case R.id.livestock_card_view:
                startActivity(new Intent(getContext(), LivestockActivity.class));
                break;
            case R.id.crops_card_view:
                startActivity(new Intent(getContext(), CropsActivity.class));
                break;
            default:
                Toast.makeText(getContext(), "No Set Action", Toast.LENGTH_SHORT).show();
        }
    }
}