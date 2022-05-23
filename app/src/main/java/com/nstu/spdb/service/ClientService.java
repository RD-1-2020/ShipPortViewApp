package com.nstu.spdb.service;

import android.widget.ArrayAdapter;

import com.nstu.spdb.cache.ClientCache;
import com.nstu.spdb.dto.ClientDto;

public class ClientService {
    private static ClientService INSTANCE;

    private final ClientRequestService clientRequestService = ClientRequestService.getInstance();
    private final ClientCache clientCache = ClientCache.getInstance();

    private ClientService() {

    }

    public static ClientService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientService();
        }

        return INSTANCE;
    }

    public void createClientWithRefreshCacheAndAdapter(ClientDto clientDto, ArrayAdapter<String> adapter) {
        clientRequestService.createClient(clientDto);

        clientCache.refreshCache();
        adapter.add(clientDto.getFullName());
    }
}
