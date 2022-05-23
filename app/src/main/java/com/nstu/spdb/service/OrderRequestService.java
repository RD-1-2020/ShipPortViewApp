package com.nstu.spdb.service;

import com.nstu.spdb.dto.OrderDto;
import com.nstu.spdb.utils.JsonUtils;

import java.util.List;

public class OrderRequestService {
    private static OrderRequestService INSTANCE;
    private static final String GET_ALL_ORDER_URL = ServerRequestService.HOST_URL + "order/getOrders";
    private static final String GET_ORDER_URL = ServerRequestService.HOST_URL + "order/getOrder?orderId=";

    private final JsonUtils jsonUtils = JsonUtils.getInstance();
    private final ServerRequestService serverRequestService = ServerRequestService.getInstance();

    public List<OrderDto> getAllOrders() {
        return jsonUtils.jsonToListWithGeneric(serverRequestService.doGetRequest(GET_ALL_ORDER_URL), OrderDto.class);
    }

    public OrderDto getOrder(String orderId) {
        return jsonUtils.fromJson(serverRequestService.doGetRequest(GET_ORDER_URL + orderId), OrderDto.class);
    }

    private OrderRequestService() {

    }

    public static OrderRequestService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderRequestService();
        }

        return INSTANCE;
    }
}
