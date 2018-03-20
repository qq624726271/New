package com.chenxiayan.newapp.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chenxiaoyan on 2018/3/19.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String adress, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(adress)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
