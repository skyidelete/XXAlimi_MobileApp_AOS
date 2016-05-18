package com.anonyblah.aos.mobapp.xxalimi.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.anonyblah.aos.mobapp.xxalimi.FeedDao;
import com.anonyblah.aos.mobapp.xxalimi.R;
import com.anonyblah.aos.mobapp.xxalimi.rest.HttpGetJsonActivity;
import com.ramotion.foldingcell.FoldingCell;

/**
 * Created by Kloc on 2016-05-10.
 */
public class FeedViewActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {
    FoldingCellListAdapter adapter;
    final SwipeDetector swipeDetector = new SwipeDetector();
    private SwipeRefreshLayout swipeRefresh;
    Boolean exist = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getListView().setOnTouchListener(swipeDetector);
        setContentView(R.layout.activity_main);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(this);
        adapter = new FoldingCellListAdapter(this, FeedDao.getDao().getFeedList());

        if(exist) {
            adapter.notifyDataSetChanged();
        } else {
            exist = true;
            setListAdapter(adapter);
        }
    }
    public void onListItemClick(ListView list, View view, int position, long id) {
            ((FoldingCell) view).toggle(false);
            adapter.registerToggle(position);

    }
    @Override
    public void onRefresh() {
        Log.i("swipe", "swiping");
        swipeRefresh.setRefreshing(true);
        startActivity(new Intent(this, HttpGetJsonActivity.class));
        swipeRefresh.setRefreshing(false);
    }
}
