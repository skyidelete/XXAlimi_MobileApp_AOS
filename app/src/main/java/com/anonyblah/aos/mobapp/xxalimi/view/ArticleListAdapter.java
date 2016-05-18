package com.anonyblah.aos.mobapp.xxalimi.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anonyblah.aos.mobapp.xxalimi.Article;
import com.anonyblah.aos.mobapp.xxalimi.R;

import java.util.List;

/**
 * Created by Kloc on 2016-05-09.
 */
public class ArticleListAdapter extends BaseAdapter {
    private List<Article> articles;
    private int num;

    public ArticleListAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public ArticleListAdapter(List<Article> articles, int num) {
        this.articles = articles;
        this.num = num;
    }

    @Override
    public int getCount() {
        if(num == 0) {
            return articles.size();
        } else {
            return num;
        }
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell_content_article_item, parent, false);

            TextView title = (TextView) convertView.findViewById(R.id.articleTitle);
            TextView description = (TextView) convertView.findViewById(R.id.articleDescription);
            title.setText(articles.get(pos).getTitle());
            description.setText(articles.get(pos).getDescript());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(pos).getLink()));
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }
}
