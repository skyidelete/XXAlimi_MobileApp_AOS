package com.anonyblah.aos.mobapp.xxalimi.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.anonyblah.aos.mobapp.xxalimi.R;
import com.anonyblah.aos.mobapp.xxalimi.rest.HttpPostStringActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kloc on 2016-05-18.
 */


public class AddFeedViewActivity extends AppCompatActivity {
    private static final String TAG = AddFeedViewActivity.class.getSimpleName();

    @Bind(R.id.input_word_search) EditText wordSearchText;
    @Bind(R.id.input_uri) EditText uriText;
    @Bind(R.id.btn_search_feed) Button searchButton;
    @Bind(R.id.btn_add_feed) Button addFeedButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        searchButton.setOnClickListener(v -> search());

    }

    public void search() {
        String query = wordSearchText.getText().toString();

        Intent intent = new Intent(getApplicationContext(), HttpPostStringActivity.class);
        intent.putExtra("query", query);

    }
}
