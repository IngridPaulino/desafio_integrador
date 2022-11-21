package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductWarehouseDTO;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.entities.Warehouse;

import java.util.List;

public interface IWarehouseService {

    /**
     * Método responsável por retornar o armazém conforme Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @return Retorna uma entidade do tipo Warehouse.
     * @throws NotFoundException - NotFoundException
     */
    Warehouse getById(Long id) throws NotFoundException;

    /**
     * Método responsável por retornar a lista de todos os armazéns cadastrados.
     * @author grupo3
     * @return Retorna uma lista contendo entidades do tipo Warehouse.
     */
    List<Warehouse> getAll();

    /**
     * Método responsável por retornar o estoque de um produto em todos os armazéns.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param id - Long
     * @return Retorna uma entidade do tipo productWarehouseDTO.
     * @throws NotFoundException - NotFoundException
     */
    ProductWarehouseDTO getAllStockProductWarehouse(Long id);
}