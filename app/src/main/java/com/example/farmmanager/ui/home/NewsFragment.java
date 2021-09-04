package com.example.farmmanager.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.R;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class NewsFragment extends Fragment {
    private TextView newsView;
    private String newsURL = "https://newsapi.org/v2/everything";
    private String newsApiKey = "eaf3a43af5b5426d921f6258c8ac6bae";
    private static final String TAG = "NewsFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsView = view.findViewById(R.id.newsView);

        getNewsDetails();
        return view;
    }

    private void getNewsDetails() {
        String topic = "Farm";
        String dateFrom = "2021-08-04";
        String sortBy = "Published";
        NewsApiClient newsApiClient = new NewsApiClient(newsApiKey);
        newsApiClient.getEverything(new EverythingRequest.Builder().q(topic).build(), new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse articleResponse) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                String title, message;
                title = articleResponse.getArticles().get(0).getTitle();
                message = articleResponse.getArticles().get(0).getDescription();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }
}
