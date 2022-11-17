package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.dto.DispatchDTO;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;
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

    @GetMapping("/packing")
    public ResponseEntity<List<PackingOrderDTO>> getAllFinishedPurchases() {
        return new ResponseEntity<>(packingAndDispatchService.getAllFinishedPurchases(), HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/packing

    @PostMapping("/save-finished-purchases")
    public ResponseEntity<List<DispatchPacking>> saveFinishedPurchases() {
        return new ResponseEntity<>(packingAndDispatchService.saveFinishedPurchases(), HttpStatus.OK);
    } // TODO: 17/11/22 produtos salva toda vez que faço a requisição



    @GetMapping("/packing-for-dispatch")
    public ResponseEntity<List<Dispatch>> getAllPackingForDispatch() {
        return new ResponseEntity<>(packingAndDispatchService.getAllPackingForDispatch(), HttpStatus.OK);
    }

   @PutMapping("/dispatch")
   public ResponseEntity<Dispatch> update(@RequestBody Dispatch dispatch) {
       return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(dispatch), HttpStatus.OK);
    }
}
