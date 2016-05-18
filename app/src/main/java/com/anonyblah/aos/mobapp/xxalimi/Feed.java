package com.anonyblah.aos.mobapp.xxalimi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kloc on 2016-05-03.
 */
public class Feed implements Serializable{
    private String title;
    private String author;
    private String img;
    private List<Article> entries;

    public Feed(String title, String author, String img, List<Article> entries) {
        this.title = title;
        this.author = author;
        this.img = img;
        this.entries = entries;
    }

    public Feed() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Article> getEntries() {
        return entries;
    }

    public void setEntries(List<Article> entries) {
        this.entries = entries;
    }
}
