package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.BatchDueDateStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.ProductAdvertising;

import java.util.List;

public interface IBatchService {

    /**
     * Método responsável por retornar o lote conforme Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @return Retorna uma entidade do tipo Batch.
     * @throws NotFoundException - NotFoundException
     */
    Batch getById(Long id) throws NotFoundException;

    /**
     * Método responsável por salvar um novo lote.
     * @author Gabriel Morais
     * @param batch - Batch
     * @return Retorna uma entidade do tipo Batch.
     */
    Batch save(Batch batch);

    /**
     * Método responsável por listar os lotes de produto conforme o Id do Produto
     * @author Amanda Zotelli
     * @param id - ProductAdvertising
     * @return Retorna uma Lista da entidade do tipo Batch.
     */
    List<Batch> findBatchByProductId(ProductAdvertising id);

    /**
     * Rota responsável por retornar os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param numberOfDays - Integer
     * @param section - String
     * @return Retorna uma entidade do tipo BatchDueDateStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    BatchDueDateStockDTO getAllDueDate(Integer numberOfDays, String section);

    /**
     * Rota responsável por retornar os lotes dentro de uma data de validade e pertençam a uma categoria de produto
     * ordenados de forma crescente.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param numberOfDays - Integer
     * @param category - String
     * @param sorting - String
     * @return Retorna uma entidade do tipo BatchDueDateStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    BatchDueDateStockDTO getAllDueDateCategory(Integer numberOfDays, String category, String sorting);
}

