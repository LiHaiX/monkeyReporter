package com.mqunar.jonsnow.net;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ironman.li on 2016/7/7.
 */
public class Network {

    OkHttpClient client = new OkHttpClient();


    public String getFromUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }


    public String getFromUrlWithToken(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("PRIVATE-TOKEN","d7fHQLV8eFa2P-gjdw_z")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String postFromUrlWithToken(String url, Map<String, String> params) throws IOException {
        FormBody.Builder paramsBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                paramsBuilder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("PRIVATE-TOKEN","d7fHQLV8eFa2P-gjdw_z")
                .post(paramsBuilder.build())
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }






}
