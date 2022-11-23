package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.service.PackingAndDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/v1/ordersByDispach")
public class PackingAndDispatchController {
    @Autowired
    public PackingAndDispatchService packingAndDispatchService;

    /**
     * Rota responsável por retornar todos os carrinhos de compra.
     * @author Ingrid Paulino
     * @return Retorna uma Lista de CartProduct.
     */
    @GetMapping("/cart-product/all")
    public ResponseEntity<List<CartProduct>> findAllCartProduct() {
        return new ResponseEntity<>(packingAndDispatchService.findAllCartProduct(), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar os carrinhos de compra com status de finalizado - coleta.
     * @author Ingrid Paulino
     * @return Retorna uma Lista de PackingOrderDTO.
     */
    @GetMapping("/packing")
    public ResponseEntity<List<PackingOrderDTO>> getAllFinishedPurchases() {
        return new ResponseEntity<>(packingAndDispatchService.getAllFinishedPurchases(), HttpStatus.OK);
    }

    /**
     * Rota responsável por salvar os produtos com status de finalizados na tabela de montagem/embalagem/embalagens que precisam ser montados.
     * @author Ingrid Paulino
     * @return Retorna uma lista de DispatchPacking
     */
    @PostMapping("/save-finished-purchases")
    public ResponseEntity<List<DispatchPacking>> saveFinishedPurchases() {
        return new ResponseEntity<>(packingAndDispatchService.saveFinishedPurchases(), HttpStatus.CREATED);
    }

    /**
     * Rota responsável por empacotar na mesma embalagem os produtos com categoria e comprador iguais.
     * @author Ingrid Paulino
     * @return Retorna uma lista de Dispatch
     */
    @PostMapping("/pack-products") //Embala pacotes/produtos
    public ResponseEntity<List<Dispatch>> packProducts() {
        return new ResponseEntity<>(packingAndDispatchService.packagedProductsFromSameBuyerAndCategory(), HttpStatus.CREATED);
    }

    /**
     * Rota responsável por listar todos os pacotes dispachados.
     * @author Ingrid Paulino
     * @return Retorna uma entidade do tipo Dispatch.
     */
    @GetMapping("/dispatched-packages")
    public ResponseEntity<List<Dispatch>> getAllPackingForDispatch() {
        return new ResponseEntity<>(packingAndDispatchService.getAllPackingForDispatch(), HttpStatus.OK);
    }

    /**
     * Rota responsável por atualizar status da entrega para entregue e encaminhar um email avisando para o comprador que o produto foi entrgue.
     * @author Ingrid Paulino
     * @param id -Long
     * @return Retorna uma entidade do tipo Dispatch ou uma string caso o pacote não sejá encontrado
     * @throws NotFoundException - NotFoundException
     */
   @PatchMapping("/dispatch/{id}")
   public ResponseEntity<Dispatch> update(@PathVariable Long id) throws NotFoundException {
       return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(id), HttpStatus.OK);
    }

    /**
     * Rota responsável por deletar as entregas com status de entrgue.
     * @author Ingrid Paulino
     * @return Retorna uma string.
     */
   @DeleteMapping("/dispatch-entreges/delete")
   public ResponseEntity<String> deleteDispatchEntregues() {
       AtomicReference<Integer> entregas = packingAndDispatchService.deleteAllPackingsEntregue();
       if(entregas.get() == 0) {
           return new ResponseEntity<>("Você realizou todas as entregas, parabéns!!", HttpStatus.OK);
       }
       return new ResponseEntity<>("Falta entregar " + entregas + " encomendas!", HttpStatus.OK);
   }
}
