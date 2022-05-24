package com.nstu.spdb.cache;

import com.nstu.spdb.dto.OrderDto;
import com.nstu.spdb.service.OrderRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class OrderCache {
    private static OrderCache INSTANCE;
    private final Map<Long, OrderDto> cache = new ConcurrentHashMap<>();
    private final OrderRequestService orderRequestService = OrderRequestService.getInstance();

    private OrderCache() {

    }

    public static OrderCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderCache();
        }

        return INSTANCE;
    }

    public void refreshCache() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (cache) {
                    cache.clear();
                    List<OrderDto> ordersDto = orderRequestService.getAllOrders();
                    if (ordersDto == null) {
                        return;
                    }

                    ordersDto.forEach(orderDto -> {
                        cache.put(orderDto.getOrderId(), orderDto);
                    });
                }
            }
        }.start();
    }

    public List<OrderDto> getCache() {
        if (cache.isEmpty()) {
            refreshCache();
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2_000L));
        }

        List<OrderDto> orderDtoList = new ArrayList<>();
        cache.forEach((id, order) -> {
            orderDtoList.add(order);
        });

        return orderDtoList;
    }
}
