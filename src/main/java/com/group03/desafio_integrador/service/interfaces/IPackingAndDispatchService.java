package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.dto.DispatchDTO;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.DispatchPacking;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IPackingAndDispatchService {
    List<PackingOrderDTO> getAllCartProductFinished();
    Set<DispatchPacking> getAllPackingForDispatch();
    void saveData();
    DispatchPacking updateStatusDispatch(DispatchPacking dispatchPacking);


}
