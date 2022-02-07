package com.example.farmmanager.ui.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.farmmanager.CalendarActivity;
import com.example.farmmanager.CropsActivity;
import com.example.farmmanager.LauncherActivity;
import com.example.farmmanager.LivestockActivity;
import com.example.farmmanager.R;
import com.example.farmmanager.Transactions;
import com.google.android.material.tabs.TabLayout;

public class HomeFragmentBottomNav extends Fragment implements View.OnClickListener {
    CardView transactionsCardView, livestockCardView, cropsCardView, notificationsCardView, calendarCardView, settingsCardView;
    TextView farmNameView, usernameView;
    private static final String TAG = "Home";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_bottom_nav, container,false);
        transactionsCardView = root.findViewById(R.id.transactions_card_view);
        livestockCardView = root.findViewById(R.id.livestock_card_view);
        cropsCardView = root.findViewById(R.id.crops_card_view);
        notificationsCardView = root.findViewById(R.id.notifications_card_view);
        calendarCardView = root.findViewById(R.id.calendar_card_view);
        settingsCardView = root.findViewById(R.id.settings_card_view);
        farmNameView = root.findViewById(R.id.farm_name_view);
        usernameView = root.findViewById(R.id.username_view);
        LauncherActivity launcherActivity = new LauncherActivity();
        Bundle bundle = getActivity().getIntent().getExtras();
        String username = bundle.getString("firstName");
        String farmName = bundle.getString("farmName");
        farmNameView.setText(farmName);
        usernameView.setText(String.format("Hi, %s", username));
        transactionsCardView.setOnClickListener(this);
        livestockCardView.setOnClickListener(this);
        cropsCardView.setOnClickListener(this);
        notificationsCardView.setOnClickListener(this);
        calendarCardView.setOnClickListener(this);
        settingsCardView.setOnClickListener(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pagerWeatherNews);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabsWeatherNews);
        tabs.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if (ID == R.id.transactions_card_view){
            startActivity(new Intent(getContext(), Transactions.class));
        } else if (ID == R.id.livestock_card_view){
            startActivity(new Intent(getContext(), LivestockActivity.class));
        } else if (ID == R.id.crops_card_view){
            startActivity(new Intent(getContext(), CropsActivity.class));
        } else if (ID == R.id.notifications_card_view){
            startActivity(new Intent(getContext(), LivestockActivity.class));
        } else if (ID == R.id.calendar_card_view){
            startActivity(new Intent(getContext(), CalendarActivity.class));
        } else if (ID == R.id.settings_card_view){
            startActivity(new Intent(getContext(), LivestockActivity.class));
        }
    }
}