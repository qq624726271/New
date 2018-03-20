package com.chenxiayan.newapp.model;

/**
 * Created by chenxiaoyan on 2018/3/19.
 */

public class Title {
    private String title;
    private String descr;
    private String imageUrl;
    private String url;

    public Title(String title, String descr, String imageUrl, String url) {
        this.title = title;
        this.descr = descr;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    private String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String uri) {
        this.url = url;
    }
}
