package com.nstu.spdb.service;

import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.utils.JsonUtils;

import java.util.List;

public class ClientRequestService {
    private static ClientRequestService INSTANCE;

    private static final String GET_ALL_CLIENT_URL = ServerRequestService.HOST_URL + "client/getClients";
    private static final String CREATE_CLIENT_URL = ServerRequestService.HOST_URL + "client/create";

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
        return jsonUtils.jsonToListWithGeneric(serverRequestService.doSyncGetRequest(GET_ALL_CLIENT_URL), ClientDto.class);
    }

    public void createClient(ClientDto clientDto) {
        String json = jsonUtils.toJson(clientDto);
        if (json == null) {
            return;
        }

        serverRequestService.doAsyncPostRequestWithNoReturn(CREATE_CLIENT_URL, json);
    }
}
