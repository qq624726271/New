package com.chenxiayan.newapp.Util;

import com.chenxiayan.newapp.gson.NewsList;
import com.google.gson.Gson;

/**
 * Created by chenxiaoyan on 2018/3/19.
 */

public class Utility {
    public static NewsList parseJsonWithGson(final String requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText,NewsList.class);
    }
}
