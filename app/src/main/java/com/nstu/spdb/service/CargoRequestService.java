package com.nstu.spdb.service;

import com.nstu.spdb.dto.CargoDto;
import com.nstu.spdb.dto.ClientDto;
import com.nstu.spdb.dto.InvoiceDto;
import com.nstu.spdb.utils.JsonUtils;

import java.util.List;

public class CargoRequestService {
    private static CargoRequestService INSTANCE;

    private static final String GET_ALL_CARGO_URL = ServerRequestService.HOST_URL + "cargo/getCargos";
    private static final String UPDATE_CARGO_URL = ServerRequestService.HOST_URL + "cargo/update?cargoId=";

    private final ServerRequestService serverRequestService = ServerRequestService.getInstance();
    private final JsonUtils jsonUtils = JsonUtils.getInstance();

    private CargoRequestService() {

    }

    public static CargoRequestService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CargoRequestService();
        }

        return INSTANCE;
    }

    public List<CargoDto> getAllCargos() {
        return jsonUtils.jsonToListWithGeneric(serverRequestService.doSyncGetRequest(GET_ALL_CARGO_URL), CargoDto.class);
    }

    public void updateCargoWithInvoice(InvoiceDto invoiceDto, Long cargoId) {
        String json = jsonUtils.toJson(invoiceDto);
        if (json == null) {
            return;
        }

        serverRequestService.doAsyncPostRequestWithNoReturn(UPDATE_CARGO_URL + cargoId, json);
    }

}
