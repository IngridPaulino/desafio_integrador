package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.service.PackingAndDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class PackingAndDispatchController {
    @Autowired
    public PackingAndDispatchService packingAndDispatchService;

    /**
     * Rota responsável por retornar as compras finalizadas.
     * @author Ingrid Paulino
     * @return Retorna uma Lista de PackingOrder.
     */
    @GetMapping("/packing")
    public ResponseEntity<List<PackingOrderDTO>> getAllFinishedPurchases() {
        return new ResponseEntity<>(packingAndDispatchService.getAllFinishedPurchases(), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar uma mensagem de sucesso se tudo ocorrer como esperado.
     * @author Ingrid Paulino
     * @return Retorna uma string
     */
    @PostMapping("/save-finished-purchases")
    public ResponseEntity<String> saveFinishedPurchases() {
        packingAndDispatchService.saveFinishedPurchases();
        return new ResponseEntity<>("salvo com sucesso!!!!", HttpStatus.CREATED);
    }

    /**
     * Rota responsável por retornar a lista dos produtos embalados.
     * @author Ingrid Paulino
     * @return Retorna uma lista de Dispatch.
     */
    @PostMapping("/pack-products") //Embala pacotes
    public ResponseEntity<List<Dispatch>> packProducts() {
        return new ResponseEntity<>(packingAndDispatchService.packagedProductsFromSameBuyerAndCategory(), HttpStatus.CREATED);
    }

    /**
     * Rota responsável por retornar a entrega que foi atualizada.
     * @author Ingrid Paulino
     * @return Retorna uma entidade do tipo Dispatch.
     */
    @GetMapping("/packing-for-dispatch")
    public ResponseEntity<List<Dispatch>> getAllPackingForDispatch() {
        return new ResponseEntity<>(packingAndDispatchService.getAllPackingForDispatch(), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar a entrega que foi atualizada.
     * @author Ingrid Paulino
     * @param id -Long
     * @return Retorna uma entidade do tipo Dispatch.
     * @throws NotFoundException - NotFoundException
     */
    @PatchMapping("/dispatch/{id}")
   public ResponseEntity<Dispatch> update(@PathVariable Long id) throws NotFoundException {
       return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(id), HttpStatus.OK);
    }
}
