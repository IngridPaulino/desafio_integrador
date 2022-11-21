package com.group03.desafio_integrador.utils.mocks;

import com.group03.desafio_integrador.dto.ProductDTO;
import com.group03.desafio_integrador.dto.PurchaseOrderDTO;
import com.group03.desafio_integrador.dto.ShoppingCartTotalDTO;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.entities_enum.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestsMocks {
    static ProductAdvertising productId = ProductAdvertising.builder().productId(1L).build();

    private static final List<Batch> batchList = new ArrayList<>();

    private static final List<ProductAdvertising> productList = new ArrayList<>();

    public static Batch mockBatch() {

        return new Batch(1L,
                productId,
                10.0F,
                15,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2022-11-30"),
                BigDecimal.valueOf(150.00));
    }

    public static Batch createBatch() {

        Batch batch = new Batch(null,
                productId,
                10.0F,
                15,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2022-11-30"),
                BigDecimal.valueOf(150.00));

        batchList.add(batch);

        return batch;

    }

    public static Batch mockUpdateBatch() {

        return new Batch(1L,
                productId,
                10.0F,
                20,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                40.0F,
                LocalDate.parse("2022-11-30"),
                BigDecimal.valueOf(200.00));

    }

    public static InboundOrder mockInboundOrder() {

        return new InboundOrder(1L,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                batchList);

    }

    public static InboundOrder mockCreateInboundOrder() {

        return new InboundOrder(null,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                batchList);
    }

    public static ProductAdvertising mockProductAdvertising() {

        return new ProductAdvertising(1L,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                "Maçã",
                "teste",
                Seller.builder().sellerId(1L).build(),
                CategoryEnum.FS,
                BigDecimal.valueOf(2),
                5
        );

    }

    public static List<ProductAdvertising> mockProductListEmpty() {
        List<ProductAdvertising> productAdvertisingsFreshList = new ArrayList<>();
        return productAdvertisingsFreshList;
    }

    public static List<ProductAdvertising> mockProductListFreshEmpty() {
        List<ProductAdvertising> productAdvertisingsFreshList = new ArrayList<>();
        return productAdvertisingsFreshList;
    }

    public static List<ProductAdvertising> mockProductListFresh() {
        List<ProductAdvertising> productAdvertisingsFreshList = new ArrayList<>();

        ProductAdvertising productFresh = new ProductAdvertising(1L,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                "Maçã",
                "teste",
                Seller.builder().sellerId(1L).build(),
                CategoryEnum.FS,
                BigDecimal.valueOf(2),
                5
        );

        productAdvertisingsFreshList.add(productFresh);
        return productAdvertisingsFreshList;
    }

    public static List<ProductAdvertising> mockProductList() {
        List<ProductAdvertising> productAdvertisingsFreshList = new ArrayList<>();

        ProductAdvertising productFresh = new ProductAdvertising(1L,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                "Maçã",
                "teste",
                Seller.builder().sellerId(1L).build(),
                CategoryEnum.FS,
                BigDecimal.valueOf(2),
                5
        );
        productAdvertisingsFreshList.add(productFresh);

        ProductAdvertising productRefrigerated = new ProductAdvertising(2L,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                "Iogurte",
                "teste",
                Seller.builder().sellerId(1L).build(),
                CategoryEnum.RF,
                BigDecimal.valueOf(2),
                10
        );

        ProductAdvertising productFrozen = new ProductAdvertising(3L,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                "Polpa de Morango",
                "teste",
                Seller.builder().sellerId(1L).build(),
                CategoryEnum.FF,
                BigDecimal.valueOf(1),
                10
        );

        productList.add(productFresh);
        productList.add(productRefrigerated);
        productList.add(productFrozen);

        return productList;

    }

    public static PurchaseOrderDTO mockCreateCartRequest() {

        List<ProductDTO> productDTOList = new ArrayList<>();

        ProductDTO productDTO = ProductDTO.builder()
                .productId(1L)
                .quantity(5)
                .build();

        productDTOList.add(productDTO);

        return PurchaseOrderDTO.builder()
                .buyerId(1L)
                .products(productDTOList)
                .build();

    }

    public static ShoppingCartTotalDTO mockCreateCartResponse() {
        return ShoppingCartTotalDTO.builder()
                .totalPrice(10.0)
                .build();
    }

    public static Buyer mockBuyer() {
        return new Buyer(
                1L,
                "Buyer 1",
                "buyer@email.com",
                "rua jose"
        );
    }

    public static ShoppingCart mockShoppingCartOpen() {
        return new ShoppingCart(
                1L,
                LocalDate.parse("2022-11-30"),
                OrderStatusEnum.ABERTO,
                mockBuyer(),
                10.0
                );
    }

    public static ShoppingCart mockShoppingCartCreate() {
        return new ShoppingCart(
                null,
                LocalDate.parse("2022-11-30"),
                OrderStatusEnum.ABERTO,
                mockBuyer(),
                10.0
        );
    }

    public static CartProduct mockCartProductCreate() {
        return new CartProduct(
                null,
                mockProductAdvertising(),
                mockShoppingCartOpen(),
                5
        );
    }

    public static List<CartProduct> mockCartProductOrderList() {

        List<CartProduct> cartProductList = new ArrayList<>();

        CartProduct cartProduct = new CartProduct(
                1L,
                mockProductAdvertising(),
                mockShoppingCartOpen(),
                5
                );

        cartProductList.add(cartProduct);

        return cartProductList;

    }

    // PUT rota para alterar o status de um pedido de ABERTO para FINALIZADO
    public static ShoppingCart mockShoppingCartFinished() {
        return new ShoppingCart(
                1L,
                LocalDate.parse("2022-11-30"),
                OrderStatusEnum.FINALIZADO,
                mockBuyer(),
                10.0
        );
    }
}
