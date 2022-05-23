package com.nstu.spdb.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServerRequestService {
    public static final String HOST_URL = "http://90.189.147.81:8182/";
    private static final String GET_ALL_ORDER_URL = HOST_URL + "order/getOrders";
    private static ServerRequestService INSTANCE;

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
                        e.printStackTrace();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res[0];
    }
}
