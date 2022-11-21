package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.dto.BatchStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.service.interfaces.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    @Autowired
    public IInboundOrderService service;

    /**
     * Rota responsável pela criação de um pedido de armazenamento.
     * @author Gabriel Morais
     * @param inboundOrder - InboundOrder
     * @return Retorna um dto do tipo BatchStockDTO.
     */
    @PostMapping
    public ResponseEntity<BatchStockDTO> save(@Valid @RequestBody InboundOrder inboundOrder) throws Exception {
        return new ResponseEntity<>(service.save(inboundOrder), HttpStatus.CREATED);
    }

    /**
     * Rota responsável por listar todos os pedidos de armazenamento.
     * @author Gabriel Morais
     * @return Retorna uma lista de Propriedades.
     */
    @GetMapping
    public ResponseEntity<List<InboundOrder>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    /**
     * Rota responsável pela alteração de um lote.
     * @author Gabriel Morais
     * @param batch - Batch
     * @return Retorna uma entidade do tipo Batch.
     */
    @PutMapping
    public ResponseEntity<Batch> update(@Valid @RequestBody Batch batch) {
        return new ResponseEntity<>(service.update(batch), HttpStatus.CREATED);
    }

}