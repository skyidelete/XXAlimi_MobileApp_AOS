package com.anonyblah.aos.mobapp.xxalimi.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anonyblah.aos.mobapp.xxalimi.Feed;
import com.anonyblah.aos.mobapp.xxalimi.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kloc on 2016-05-11.
 */
public class ArticleViewActivity extends ListActivity {
    ArticleListAdapter adapter;
    Feed input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.article_detail_view);

        Intent intent = getIntent();
        this.input = (Feed)intent.getSerializableExtra("input");

        ListView listView = (ListView)findViewById(R.id.articleDetailListView);
        adapter = new ArticleListAdapter(input.getEntries(), 0);
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.cell_title_layout, null, false));
        TextView title = (TextView)findViewById(R.id.feedTitle);
        title.setText(input.getTitle());
        ImageView img = (ImageView)findViewById(R.id.feedLogo);
        Bitmap bitmap = null;
        try {
            if(android.os.Build.VERSION.SDK_INT > 9) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

            }
            URLConnection conn = new URL(input.getImg()).openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Image Load Fail");
        }
        if(bitmap != null) {
            img.setImageBitmap(bitmap);
        } else {
            img.setImageResource(R.drawable.ic_broken_image_white_24dp);
        }
        listView.setAdapter(adapter);
    }
}