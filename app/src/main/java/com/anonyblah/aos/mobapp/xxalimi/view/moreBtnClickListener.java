package com.anonyblah.aos.mobapp.xxalimi.view;

import android.view.View;

import com.anonyblah.aos.mobapp.xxalimi.Feed;

/**
 * Created by Kloc on 2016-05-11.
 */
public abstract class moreBtnClickListener implements View.OnClickListener {
    Feed feed;

    public moreBtnClickListener(Feed feed) {
        this.feed = feed;
    }
}
