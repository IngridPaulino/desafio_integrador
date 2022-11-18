package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface IPackingAndDispatchService {

    List<PackingOrderDTO> getAllFinishedPurchases();
    List<Dispatch> getAllPackingForDispatch();

    /**
     * Método responsável por salvar as compras finalizadas na tabela de embalagem.
     * @author Ingrid Paulino
     * @return Não tem retorno
     */
    void saveFinishedPurchases();

    /**
     * Método responsável por retornar o status da entrega atualizada.
     * @author Ingrid Paulino
     * @param id - Long
     * @return Dispatch - Retorna uma entidade do tipo Dispatch.
     * @throws NotFoundException
     */
    Dispatch updateStatusDispatch(Long id) throws NotFoundException;
    List<Dispatch> packagedProductsFromSameBuyerAndCategory();

    AtomicReference<Integer> deleteAllPackingsEntregue();
}
