package com.example.farmmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.adapters.NewsRecyclerAdapter;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {
    private RecyclerView newsRecycler;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    List<Source> newsArray = new ArrayList<>();
    private static final String TAG = "News";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     setContentView(R.layout.activity_news);

        Toolbar toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getNewsDetails();
        newsRecycler = findViewById(R.id.newsRecycler);
        newsRecycler.setHasFixedSize(true);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        newsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void getNewsDetails() {
        String topic = "Farm";
        String dateFrom = "2021-08-04";
        String sortBy = "Published";
        String newsApiKey = "eaf3a43af5b5426d921f6258c8ac6bae";
        NewsApiClient newsApiClient = new NewsApiClient(newsApiKey);
        newsApiClient.getSources(new SourcesRequest.Builder().language("en").build(), new NewsApiClient.SourcesCallback() {
            @Override
            public void onSuccess(SourcesResponse sourcesResponse) {
                newsArray.addAll(sourcesResponse.getSources());
                newsRecyclerAdapter = new NewsRecyclerAdapter(newsArray);
                newsRecycler.setAdapter(newsRecyclerAdapter);
            }

            @Override
            public void onFailure(Throwable throwable) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setTitle("News Error");
                alertDialog.setMessage(throwable.getLocalizedMessage());
                alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
    }
}