package com.example.farmmanager.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.R;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;

public class NewsFragment extends Fragment {
    private TextView newsTitle1, newsTitle2, newsMessage1, newsMessage2;
    private static final String TAG = "NewsFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsTitle1 = view.findViewById(R.id.news_title1);
        newsTitle2 = view.findViewById(R.id.news_title2);
        newsMessage1 = view.findViewById(R.id.news_message1);
        newsMessage2 = view.findViewById(R.id.news_message2);

        getNewsDetails();
        return view;
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
                Log.d(TAG, "onSuccess: " + sourcesResponse.getSources().size());
                String title1, message1, title2, message2;
                title1 = sourcesResponse.getSources().get(0).getName();
                message1 = sourcesResponse.getSources().get(0).getDescription();
                title2 = sourcesResponse.getSources().get(1).getName();
                message2 = sourcesResponse.getSources().get(1).getDescription();

                newsTitle1.setText(title1);
                newsMessage1.setText(message1);
                newsTitle2.setText(title2);
                newsMessage2.setText(message2);
            }

            @Override
            public void onFailure(Throwable throwable) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("News Error");
                alertDialog.setMessage(throwable.getLocalizedMessage());
                alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
    }
}
