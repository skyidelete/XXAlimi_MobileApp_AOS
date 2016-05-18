package com.anonyblah.aos.mobapp.xxalimi.rest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.anonyblah.aos.mobapp.xxalimi.AbstractAsyncListActivity;
import com.anonyblah.aos.mobapp.xxalimi.view.SearchResultListAdapter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kloc on 2016-05-18.
 */
public class HttpFeedSearchActivity extends AbstractAsyncListActivity {
    protected static final String TAG = HttpFeedSearchActivity.class.getSimpleName();

    private String query;
    private List<SearchResult> results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getIntent().getCharSequenceExtra("query").toString();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GoogleFeedSearchTask().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if(this.results == null) {
            return;
        }
        SearchResult result = this.results.get(position);
        Intent intent = new Intent(this, HttpPostStringActivity.class);
        intent.putExtra("link", result.getUrl());
        startActivity(intent);
    }

    private void refreshResults(SearchResponse response) {
        if (response == null) {
            return;
        }
        this.results = response.getResponseData().getResults();
        setListAdapter(new SearchResultListAdapter(this, this.results));
    }

    private class GoogleFeedSearchTask extends AsyncTask<Void, Void, SearchResponse> {
        @Override
        protected SearchResponse doInBackground(Void... params) {
            try {
                final String url = "https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q={query}";

                RestTemplate restTemplate = new RestTemplate();

                GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
                messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript")));
                restTemplate.getMessageConverters().add(messageConverter);

                SearchResponse response = restTemplate.getForObject(url, SearchResponse.class, query);

                return response;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(SearchResponse searchResponse) {
            dismissProgressDialog();

        }
    }

}
