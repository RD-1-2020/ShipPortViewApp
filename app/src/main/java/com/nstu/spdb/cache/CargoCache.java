package com.nstu.spdb.cache;

import com.nstu.spdb.dto.CargoDto;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.service.CargoRequestService;
import com.nstu.spdb.service.ClientRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class CargoCache {
    private static CargoCache INSTANCE;
    private final Map<Long, CargoDto> cache = new ConcurrentHashMap<>();
    private final CargoRequestService clientRequestService = CargoRequestService.getInstance();

    private CargoCache() {

    }

    public static CargoCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CargoCache();
        }

        return INSTANCE;
    }

    public void refreshCache() {
        new Thread() {
            @Override
            public void run() {
                synchronized (cache) {
                    super.run();
                    cache.clear();
                    List<CargoDto> cargoDtos = clientRequestService.getAllCargos();
                    if (cargoDtos == null) {
                        return;
                    }

                    cargoDtos.forEach(orderDto -> {
                        cache.put(orderDto.getId(), orderDto);
                    });
                }
            }
        }.start();
    }

    public List<CargoDto> getCache() {
        if (cache.isEmpty()) {
            refreshCache();
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2_000L));
        }

        List<CargoDto> cargoDtos = new ArrayList<>();
        cache.forEach((id, cargoDto) -> {
            cargoDtos.add(cargoDto);
        });

        return cargoDtos;
    }
}
