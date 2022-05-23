package com.nstu.spdb.cache;

import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.service.ClientRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ClientCache {
    private static ClientCache INSTANCE;
    private final Map<Long, ClientDto> cache = new ConcurrentHashMap<>();
    private final ClientRequestService clientRequestService = ClientRequestService.getInstance();

    private ClientCache() {

    }

    public static ClientCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientCache();
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
                    List<ClientDto> ordersDto = clientRequestService.getAllClients();
                    if (ordersDto == null) {
                        return;
                    }

                    ordersDto.forEach(orderDto -> {
                        cache.put(orderDto.getId(), orderDto);
                    });
                }
            }
        }.start();
    }

    public List<ClientDto> getCache() {
        if (cache.isEmpty()) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2_000L));
        }

        List<ClientDto> clientDtos = new ArrayList<>();
        cache.forEach((id, clientDto) -> {
            clientDtos.add(clientDto);
        });

        return clientDtos;
    }
}
