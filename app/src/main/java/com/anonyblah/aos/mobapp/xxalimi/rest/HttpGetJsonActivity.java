/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anonyblah.aos.mobapp.xxalimi.rest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.anonyblah.aos.mobapp.xxalimi.AbstractAsyncActivity;
import com.anonyblah.aos.mobapp.xxalimi.Feed;
import com.anonyblah.aos.mobapp.xxalimi.FeedDao;
import com.anonyblah.aos.mobapp.xxalimi.R;
import com.anonyblah.aos.mobapp.xxalimi.view.FeedViewActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */

public class HttpGetJsonActivity extends AbstractAsyncActivity {
    protected static final String TAG = HttpGetJsonActivity.class.getSimpleName();
    private boolean changedFlag;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changedFlag = false;
    }
    @Override
    public void onStart() {
        super.onStart();
        new DownloadTask().execute();
    }

    private class DownloadTask extends AsyncTask<Void, Void, List<Feed>> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            changedFlag = false;
        }

        @Override
        protected List<Feed> doInBackground(Void... params) {
            try {
                final String url = getString(R.string.server_uri) + "/home";

                HttpHeaders requestHeaders = new HttpHeaders();
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(acceptableMediaTypes);
                requestHeaders.setUserAgent("mobapp/aos");

                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

                ResponseEntity<Feed[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Feed[].class);
                Log.i(TAG, Arrays.asList(responseEntity.getBody()).get(0).getTitle());
                FeedDao.getDao().addFeedList(Arrays.asList(responseEntity.getBody()));
                return Arrays.asList(responseEntity.getBody());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Feed> result) {
            dismissProgressDialog();
            Intent intent = new Intent(getApplicationContext(), FeedViewActivity.class);
            intent.putExtra("Changed", changedFlag);
            startActivity(intent);
        }
    }

}
