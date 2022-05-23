package com.nstu.spdb.service;

import com.nstu.spdb.dto.OrderDto;
import com.nstu.spdb.utils.JsonUtils;

import java.util.List;

public class OrderRequestService {
    private static OrderRequestService INSTANCE;
    private static final String GET_ALL_ORDER_URL = ServerRequestService.HOST_URL + "order/getOrders";
    private static final String GET_ORDER_URL = ServerRequestService.HOST_URL + "order/getOrder?orderId=";

    private static final String CREATE_ORDER_URL = ServerRequestService.HOST_URL + "order/create";

    private final JsonUtils jsonUtils = JsonUtils.getInstance();
    private final ServerRequestService serverRequestService = ServerRequestService.getInstance();

    public List<OrderDto> getAllOrders() {
        return jsonUtils.jsonToListWithGeneric(serverRequestService.doSyncGetRequest(GET_ALL_ORDER_URL), OrderDto.class);
    }

    public OrderDto getOrder(String orderId) {
        return jsonUtils.fromJson(serverRequestService.doSyncGetRequest(GET_ORDER_URL + orderId), OrderDto.class);
    }

    public void createOrder(OrderDto orderDto) {
        String json = jsonUtils.toJson(orderDto);
        if (json == null) {
            return;
        }

        serverRequestService.doAsyncPostRequestWithNoReturn(CREATE_ORDER_URL, json);
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
