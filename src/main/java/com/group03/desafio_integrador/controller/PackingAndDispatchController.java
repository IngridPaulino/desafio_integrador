package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.dto.DispatchDTO;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.service.PackingAndDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/orders")
public class PackingAndDispatchController {
    @Autowired
    public PackingAndDispatchService packingAndDispatchService;

    @GetMapping("/packing")
    public ResponseEntity<List<PackingOrderDTO>> getAllCartProductFinished() {
        return new ResponseEntity<>(packingAndDispatchService.getAllCartProductFinished(), HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/packing

    @GetMapping("/packing2")
    public ResponseEntity<List<DispatchDTO>> getAllPackingFinished() {
        return new ResponseEntity<>(packingAndDispatchService.packingsfinal(), HttpStatus.OK);
    } //http://localhost:8080/api/v1/orders/packing2

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

   // @PutMapping("/dispatch/{id}")
    //public ResponseEntity<DispatchDTO> update(@PathVariable(value = "id") Long id) {
    //    return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(id), HttpStatus.OK);
   // }

   @PutMapping("/dispatch")
   public ResponseEntity<DispatchPacking> update(@RequestBody DispatchPacking dispatch) {
       return new ResponseEntity<>(packingAndDispatchService.updateStatusDispatch(dispatch), HttpStatus.OK);
    }
}
