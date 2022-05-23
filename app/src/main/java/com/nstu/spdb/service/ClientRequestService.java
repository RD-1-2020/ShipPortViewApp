package com.nstu.spdb.service;

import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.utils.JsonUtils;

import java.util.List;

public class ClientRequestService {
    private static ClientRequestService INSTANCE;

    private static final String GET_ALL_CLIENT_URL = ServerRequestService.HOST_URL + "order/getOrders";
    private final ServerRequestService serverRequestService = ServerRequestService.getInstance();
    private final JsonUtils jsonUtils = JsonUtils.getInstance();

    private ClientRequestService() {

    }

    public static ClientRequestService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientRequestService();
        }

        return INSTANCE;
    }

    public List<ClientDto> getAllClients() {
        return jsonUtils.jsonToListWithGeneric(serverRequestService.doGetRequest(GET_ALL_CLIENT_URL), ClientDto.class);
    }
}
