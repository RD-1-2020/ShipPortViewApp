package com.nstu.spdb.service;

import android.widget.ArrayAdapter;

import com.nstu.spdb.cache.OrderCache;
import com.nstu.spdb.dto.OrderDto;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class OrderService {
    private static OrderService INSTANCE;

    private final OrderRequestService orderRequestService = OrderRequestService.getInstance();
    private final OrderCache orderCache = OrderCache.getInstance();

    private OrderService() {

    }

    public static OrderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderService();
        }

        return INSTANCE;
    }

    public void createOrderWithRefreshCacheAndAdapter(OrderDto orderDto, ArrayAdapter<String> adapter) {
        orderRequestService.createOrder(orderDto);

        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500L));
        orderCache.refreshCache();
        adapter.clear();
        orderCache.getCache().forEach(cacheItem -> {
            adapter.add(cacheItem.getOrderId() + StringUtils.SPACE + cacheItem.getStatusTitle());
        });
    }
}
