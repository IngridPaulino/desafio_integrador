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
    public ResponseEntity<List<PackingOrderDTO>> getAllCartProductFinished() {
        return new ResponseEntity<>(packingAndDispatchService.getAllCartProductFinished(), HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/packing



    @GetMapping("/packingForDispatch")
    public ResponseEntity<List<Dispatch>> getAllPackingForDispatch() {
        return new ResponseEntity<>(packingAndDispatchService.getAllPackingForDispatch(), HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/packingForDispatch

    @GetMapping("/dispatch")
    public ResponseEntity<String> getCartProductsListDispatch() {
        packingAndDispatchService.getAllPackingForDispatch();
        return new ResponseEntity<>("Salvo com sucesso !!!!!", HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/dispatch

    @PostMapping("/dispatch/save")
    public ResponseEntity<String> saveDispatch() {
        packingAndDispatchService.saveData();
        return new ResponseEntity<>("Deu certo #salvou!!", HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/dispatch/save

   @PutMapping("/dispatch")
   public ResponseEntity<Dispatch> update(@RequestBody Dispatch dispatch) {
       return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(dispatch), HttpStatus.OK);
    }
}
