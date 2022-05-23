package com.nstu.spdb.service;

import com.nstu.spdb.cache.CargoCache;
import com.nstu.spdb.dto.InvoiceDto;

public class CargoService {
    private static CargoService INSTANCE;

    private final CargoRequestService cargoRequestService = CargoRequestService.getInstance();
    private final CargoCache cargoCache = CargoCache.getInstance();

    private CargoService() {

    }

    public static CargoService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CargoService();
        }

        return INSTANCE;
    }

    public void createCargoWithRefreshCache(InvoiceDto cargoDto, String cargoId) {
        cargoRequestService.updateCargoWithInvoice(cargoDto, Long.valueOf(cargoId));

        cargoCache.refreshCache();
    }
}
