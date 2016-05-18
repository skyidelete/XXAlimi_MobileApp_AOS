package com.anonyblah.aos.mobapp.xxalimi;

import java.io.Serializable;

/**
 * Created by Kloc on 2016-05-03.
 */
public class Article implements Serializable {
    private String title;
    private String descript;
    private String author;
    private String link;
    private String updated;

    public Article(String title, String descript, String author, String link, String updated) {
        this.title = title;
        this.descript = descript;
        this.author = author;
        this.link = link;
        this.updated = updated;
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
