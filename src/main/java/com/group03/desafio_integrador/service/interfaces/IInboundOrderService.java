package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.BatchStockDTO;
import com.group03.desafio_integrador.dto.ProductWarehouseStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.InboundOrder;

import java.util.List;

public interface IInboundOrderService {

    /**
     * Método responsável por salvar um novo lote no pedido de ordem do armazém.
     * @author Gabriel Morais
     * @param inboundOrder - InboundOrder
     * @return BatchStockDTO - Retorna um dto do tipo BatchStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    // TODO: implementar Exception específica
    BatchStockDTO save(InboundOrder inboundOrder) throws Exception;

    /**
     * Método responsável por listar todos os pedidos de ordem do armazém.
     * @author Gabriel Morais
     * @return Retorna uma lista de entidades do tipo InboundOrder.
     */
    List<InboundOrder> getAll();

    /**
     * Método responsável por atualizar as informações do lote contido no armazém.
     * @author Gabriel Morais
     * @param batch - Batch
     * @return Retorna uma entidade do tipo Batch.
     */
    Batch update(Batch batch);

    /**
     * Método responsável por retornar uma lista de ProductWarehouseStockDTO com os lotes de determinado produto .
     * @author Carolina Hakamada
     * @param productId - Long
     * @return Retorna uma lista de ProductWarehouseDTO com todos os lotes de um produto em seu armazém e seção.
     * @throws Exception - Exception
     */
    List<ProductWarehouseStockDTO> getAllProductWarehouseStock(Long productId) throws Exception;

    /**
     * Método responsável por retornar uma lista de ProductWarehouseStockDTO com lotes ordenados.
     * @author Amanda Zotelli, Rosalia Padoin
     * @param productWarehouseStockDTOList - lista de entidades do tipo ProductWarehouseStockDTO
     * @param sorting - String
     * @return Retorna uma lista de ProductWarehouseDTO ordenada pelo parâmetro definido.
     */
    List<ProductWarehouseStockDTO> getAllOrdinancesForBatches(List<ProductWarehouseStockDTO> productWarehouseStockDTOList, String sorting);
}
