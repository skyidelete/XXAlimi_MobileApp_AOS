package com.anonyblah.aos.mobapp.xxalimi.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anonyblah.aos.mobapp.xxalimi.Feed;
import com.anonyblah.aos.mobapp.xxalimi.R;
import com.ramotion.foldingcell.FoldingCell;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kloc on 2016-05-09.
 */
public class FoldingCellListAdapter extends ArrayAdapter<Feed> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();


    public FoldingCellListAdapter(Context context, List<Feed> objects) {
        super(context, 0, objects);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Feed feed = getItem(position);
        FeedViewHolder viewHolder;
        FoldingCell cell = (FoldingCell)convertView;
        if(cell == null) {
            viewHolder = new FeedViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            cell = (FoldingCell)inflater.inflate(R.layout.cell, parent, false);
            viewHolder.title = (TextView)cell.findViewById(R.id.feedTitle);
            viewHolder.author = (TextView)cell.findViewById(R.id.feedAuthor);
            viewHolder.logo = (ImageView)cell.findViewById(R.id.feedLogo);
            viewHolder.counter = (TextView)cell.findViewById(R.id.counter);
            viewHolder.updated = (TextView)cell.findViewById((R.id.updated));
            viewHolder.articles = (ListView)cell.findViewById(R.id.articleListView);
            viewHolder.inner_title = (TextView)cell.findViewById(R.id.innerFeedTitle);
            viewHolder.innerBackBtn = (ImageView)cell.findViewById(R.id.innerBackBtn);
            viewHolder.articleFooter = LayoutInflater.from(getContext()).inflate(R.layout.cell_content_article_footer, parent, false);
            viewHolder.moreBtn = (TextView)viewHolder.articleFooter.findViewById(R.id.moreBtn);
            cell.setTag(viewHolder);
        } else {
            if(unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (FeedViewHolder) cell.getTag();
        }
        viewHolder.title.setText(feed.getTitle());
        viewHolder.inner_title.setText(feed.getTitle());
        viewHolder.author.setText(feed.getAuthor());
        viewHolder.counter.setText("N/A" + "개의 새글");
        viewHolder.updated.setText("N/A분" + " 전");


        Bitmap bitmap = null;
        try {
            if(android.os.Build.VERSION.SDK_INT > 9) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

            }
            URLConnection conn = new URL(feed.getImg()).openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Image Load Fail");
        }
        if(bitmap != null) {
            viewHolder.logo.setImageBitmap(bitmap);
        } else {
            viewHolder.logo.setImageResource(R.drawable.ic_broken_image_white_24dp);
        }

        viewHolder.innerBackBtn.setOnClickListener(new BackBtnClickListener(cell) {
            @Override
            public void onClick(View v) {
                cell.fold(false);
            }
        });

        viewHolder.articles.addFooterView(viewHolder.articleFooter);
        viewHolder.articles.setAdapter(new ArticleListAdapter(feed.getEntries(), 3));

        viewHolder.moreBtn.setOnClickListener(new moreBtnClickListener(feed) {
            @Override
            public void onClick(View v) {
                Log.i("moreBtn", "Button Clicked");
                Intent intent = new Intent(getContext(), ArticleViewActivity.class);
                intent.putExtra("input", feed);
                intent.putExtra("layout", R.layout.article_detail_view);
                getContext().startActivity(intent);
            }
        });
        Log.i("Feeds", String.valueOf(feed.hashCode()));
        return cell;
    }

    public void registerToggle(int position) {
        if(unfoldedIndexes.contains(position)) {
            registerFold(position);
        } else {
            registerUnfold(position);
        }
    }
    public void registerFold(int position)      { unfoldedIndexes.remove(position); }
    public void registerUnfold(int position)    { unfoldedIndexes.add(position); }

    private static class FeedViewHolder {
        TextView title;
        TextView author;
        ImageView logo;
        TextView counter;
        TextView updated;
        ListView articles;
        TextView inner_title;
        TextView moreBtn;
        ImageView innerBackBtn;
        View articleFooter;
    }
}
