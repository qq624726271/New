package com.chenxiayan.newapp.gson;

import java.util.List;

/**
 * Created by chenxiaoyan on 2018/3/19.
 */

public class NewsList {
    /**
     * code : 200
     * msg : ok
     * newslist : []
     */

    private int code;
    private String msg;
    private List<News> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<News> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<News> newslist) {
        this.newslist = newslist;
    }
}
