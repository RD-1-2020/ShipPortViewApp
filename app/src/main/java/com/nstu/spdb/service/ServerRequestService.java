package com.nstu.spdb.service;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerRequestService {
    private final static String LOG_TAG = ServerRequestService.class.getName();

    public static final String HOST_URL = "http://90.189.147.81:8181/";
    private static ServerRequestService INSTANCE;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private ServerRequestService() {

    }

    public static ServerRequestService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerRequestService();
        }

        return INSTANCE;
    }

    public String doSyncGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        final String[] res = {""};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        Log.e(LOG_TAG, "Something went wrong in send request to url:" + url + "...", e);
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.body() != null) {
                            res[0] = response.body().string();
                        }

                        countDownLatch.countDown();
                    }
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            Log.e(LOG_TAG, "Something went wrong in send request to url:" + url + "...", exception);
        }

        return res[0];
    }

    public void doAsyncPostRequestWithNoReturn(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        Log.e(LOG_TAG, "Something went wrong in send request to url:" + url + "with body: \n" + json + "\n", e);
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        // no return
                    }
                });
    }
}
