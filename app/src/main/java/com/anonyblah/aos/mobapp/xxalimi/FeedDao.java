package com.anonyblah.aos.mobapp.xxalimi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kloc on 2016-05-06.
 */
public class FeedDao {
    private static FeedDao instance;
    private List<Feed> feedList;

    private FeedDao() {
        feedList = new ArrayList<Feed>();
    }
    private boolean checkOverlap(Feed input, int position) {
        return feedList.get(position).getEntries().equals(input.getEntries());

    }
    /*
    private boolean checkOverlap(List<Feed> input) {
        boolean changedFlag = false;
        if(feedList.size() == input.size()) {
            int cnt=0;
            for(int i=0;i<input.size();i++) {
                Feed temp = input.get(i);
                if(getFeed(temp.getTitle()) != null) {
                    cnt++;
                }
            }
            if(feedList.size() == cnt);
        } else {
            feedList.clear();
            feedList.addAll(input);
        }
    }
    */
    public static FeedDao getDao() {
        if(instance == null)    instance = new FeedDao();
        return instance;
    }
    public void addFeed(Feed object) {
        feedList.add(object);
    }
    public void addAllFeed(Collection<Feed> objects) {
        feedList.addAll(objects);
    }
    public void addFeedList(List<Feed> feedList) {
        this.feedList.clear();
        this.feedList.addAll(feedList);
    }
    public Feed getFeed(int position) {
        return feedList.get(position);
    }
    public Feed getFeed(String title) {
        for(Feed feed : feedList) {
            if(feed.getTitle().equals(title)) {
                return feed;
            }
        }
        return null;
    }
    public List<Feed> getFeedList() { return feedList; }


}
