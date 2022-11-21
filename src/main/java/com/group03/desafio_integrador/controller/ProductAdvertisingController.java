package com.group03.desafio_integrador.controller;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductWarehouseStockDTO;
import com.group03.desafio_integrador.dto.PurchaseOrderDTO;
import com.group03.desafio_integrador.dto.ShoppingCartTotalDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import com.group03.desafio_integrador.entities.ShoppingCart;
import com.group03.desafio_integrador.service.interfaces.ICartProductService;
import com.group03.desafio_integrador.service.interfaces.IInboundOrderService;
import com.group03.desafio_integrador.service.interfaces.IProductAdvertisingService;
import com.group03.desafio_integrador.service.interfaces.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductAdvertisingController {

    @Autowired
    private IProductAdvertisingService service;

    @Autowired
    private ICartProductService cartService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IInboundOrderService inboundOrderService;

    /**
     * Rota responsável por retornar todos os produtos cadastrados.
     *
     * @return Retorna uma entidade do tipo ProductAdvertising.
     * @author Mariana Saraiva
     * @throws NotFoundException - NotFoundException
     */
    @GetMapping
    public ResponseEntity<List<ProductAdvertising>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar todos os produtos cadastrados por categoria.
     *
     * @param category - String
     * @return Retorna uma lista contendo entidades do tipo ProductAdvertising conforme categoria.
     * @author Mariana Saraiva
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProductAdvertising>> getAllByCategory(@RequestParam String category) {
        return new ResponseEntity<>(service.getAllByCategory(category), HttpStatus.OK);
    }

    /**
     * Rota responsável por salvar um pedido com a lista de produtos com o request do tipo PurchaseOrder
     * @author Amanda Zotelli
     * @param purchase - PurchaseOrderDTO
     * @return Retorna um dto do tipo ShoppingCartTotalDTO.
     */
    @PostMapping("/orders")
    public ResponseEntity<ShoppingCartTotalDTO> registerOrder(@RequestBody PurchaseOrderDTO purchase) {
        return new ResponseEntity<>(service.registerOrder(purchase), HttpStatus.CREATED);
    }

    /**
     * Rota responsável por listar os produtos no pedido.
     * @author Amanda Zotelli
     * @param id - Long
     * @return Retorna uma lista de entidades do tipo CartProduct.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<List<CartProduct>> getCartProducts(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(cartService.getCartProducts(id), HttpStatus.OK);
    }

    /**
     * Rota responsável por modificar o status do pedido existente para FINALIZADO.
     * @author Amanda Zotelli
     * @param orderId - Long
     * @return ShoppingCart- Retorna uma entidade do tipo ShoppingCart.
     * @throws NotFoundException - NotFoundException
     */
    @PutMapping("/orders")
    public ResponseEntity<ShoppingCart> updateCartStatus(@RequestParam Long orderId) {
        return new ResponseEntity<>(shoppingCartService.update(orderId), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar os lotes onde determinado produto aparece em seu armazém e seção.
     * @author Amanda Zotelli, Carol Hakamada, Gabriel Morais, Ingrid Paulino, Mariana Saraiva e Rosalia Padoin
     * @param productId - Long
     * @return Retorna uma Lista de ProductWarehouseDTO.
     * @throws Exception - Exception
     */
    @GetMapping(value="/list", params={"productId"})
    public ResponseEntity<List<ProductWarehouseStockDTO>> getAllProductWarehouseStock(@RequestParam("productId") Long productId) throws Exception {
        return new ResponseEntity<>(inboundOrderService.getAllProductWarehouseStock(productId), HttpStatus.OK);
    }

    /**
     * Rota responsável por retornar os lotes onde determinado produto aparece em seu armazém e seção, ordenados por um determinado parâmetro.
     * @author Amanda Zotelli e Rosalia Padoin
     * @param productId - Long
     * @return Retorna uma Lista de ProductWarehouseDTO.
     * @throws NotFoundException - NotFoundException
     */
    @GetMapping(value= "/list", params={"productId", "sorting"})
    public ResponseEntity<List<ProductWarehouseStockDTO>> getAllOrdinancesForBatches(
            @RequestParam("productId") Long productId,
            @RequestParam("sorting") String sorting
    ) throws Exception {
        List<ProductWarehouseStockDTO> productWarehouseStockDTOList = inboundOrderService.getAllProductWarehouseStock(productId);
        return new ResponseEntity<>(inboundOrderService.getAllOrdinancesForBatches(productWarehouseStockDTOList, sorting), HttpStatus.OK);
    }

}
