package com.anonyblah.aos.mobapp.xxalimi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anonyblah.aos.mobapp.xxalimi.R;
import com.anonyblah.aos.mobapp.xxalimi.rest.SearchResult;

import java.util.List;

/**
 * Created by Kloc on 2016-05-18.
 */
public class SearchResultListAdapter extends BaseAdapter {
    private List<SearchResult> results;
    private final LayoutInflater layoutInflater;

    public SearchResultListAdapter(Context context, List<SearchResult> results) {
        this.results = results;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.results != null ? this.results.size() : 0;
    }

    @Override
    public SearchResult getItem(int position) {
        return this.results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.feed_search_result_item, parent, false);
        }

        SearchResult result = getItem(position);

        if(result != null) {
            TextView title = (TextView)convertView.findViewById(R.id.title);
            title.setText(removeMarkup(result.getTitle()));
            TextView content = (TextView)convertView.findViewById(R.id.content);
            content.setText(removeMarkup(result.getContentSnippet()));
        }
        return convertView;
    }
    private String removeMarkup(String s) {
        return s.replace("<b>", "").replace("</b>", "");
    }
}
