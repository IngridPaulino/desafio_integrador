package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;

import java.util.List;

public interface IPackingAndDispatchService {
    List<PackingOrderDTO> getAllFinishedPurchases();
    List<Dispatch> getAllPackingForDispatch();
    List<DispatchPacking> saveFinishedPurchases();
    Dispatch updateStatusDispatch(Dispatch dispatchPacking);
    List<Dispatch> packagedProductsFromSameBuyerAndCategory();
}
