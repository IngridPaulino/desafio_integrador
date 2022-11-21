package com.group03.desafio_integrador.utils.mocks;

import com.group03.desafio_integrador.dto.*;
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

    private static final List<Batch> sortBatchList = new ArrayList<>();

    private static final List<Batch> batchListWithBatchId = new ArrayList<>();

    private static final List<ProductAdvertising> productList = new ArrayList<>();

    public static Batch mockBatch() {

        Batch batch = new Batch(1L,
                productId,
                10.0F,
                15,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2022-12-30"),
                BigDecimal.valueOf(150.00));

        batchListWithBatchId.add(batch);
        return batch;
    }

    public static Batch createBatch() {

        Batch batch = new Batch(null,
                productId,
                10.0F,
                15,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2022-12-30"),
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
                LocalDate.parse("2022-12-30"),
                BigDecimal.valueOf(200.00));

    }



    public static InboundOrder mockInboundOrder() {
        // TODO: 17/11/22 criei a lista pois estava vazia 
        batchList.add(TestsMocks.mockBatch());

        return new InboundOrder(1L,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                batchListWithBatchId);

    }

    public static InboundOrder mockCreateInboundOrder() {

        // TODO: 17/11/22 criei a lista pois estava vazia
        batchList.add(TestsMocks.createBatch());

        return new InboundOrder(null,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                batchList);
    }

    public static List<InboundOrder> mockCreateInboundOrderList() {
        List<InboundOrder> inboundOrderList = new ArrayList<>();

        InboundOrder inboundOrder = new InboundOrder(1L,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                batchList);

        inboundOrderList.add(inboundOrder);

        return inboundOrderList;
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
    public static PurchaseOrderDTO mockErrorCartRequest() {

        List<ProductDTO> productDTOList = new ArrayList<>();

        ProductDTO productDTO = ProductDTO.builder()
                .productId(55L)
                .quantity(5)
                .build();

        productDTOList.add(productDTO);

        return PurchaseOrderDTO.builder()
                .buyerId(1L)
                .products(productDTOList)
                .build();

    }

    public static InboundOrder mockCreateSortInboundOrder() {

        Batch expiringSoonerBatch = new Batch(null,
                productId,
                10.0F,
                5,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2022-12-30"),
                BigDecimal.valueOf(150.00));

        sortBatchList.add(expiringSoonerBatch);

        Batch batch = new Batch(null,
                productId,
                10.0F,
                5,
                LocalDate.parse("2022-11-30"),
                LocalDateTime.of(2022, 11, 9, 11, 43, 0),
                30.0F,
                LocalDate.parse("2023-02-25"),
                BigDecimal.valueOf(150.00));

        sortBatchList.add(batch);

        return new InboundOrder(null,
                LocalDate.parse("2022-11-09"),
                Section.builder().sectionId(1L).build(),
                Warehouse.builder().warehouseId(1L).build(),
                sortBatchList);
}

    public static BatchDueDateDTO mockBatchDueDateDTO() {
        return new BatchDueDateDTO(
                1L,
                1L,
                0L,
                LocalDate.parse("2022-12-30"),
                3
        );
    }

    public static BatchDueDateStockDTO mockbatchDueDateStockDTO(){
        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();

        batchDueDateDTOList.add(mockBatchDueDateDTO());

        return BatchDueDateStockDTO.builder()
                .batchDueDateStock(batchDueDateDTOList)
                .build();
    }

    public static Warehouse mockWarehouse() {
        Manager manager = Manager.builder()
                .managerId(1L)
                .build();
        return Warehouse.builder()
                .warehouseId(1L)
                .capacity(3000.0)
                .manager(manager)
                .build();
    }

    public static List<ProductWarehouseStockDTO> mockProductWarehouseStockDTOList() {
        List<BatchDTO> batchDTOList = new ArrayList<>();
        List<ProductWarehouseStockDTO> productWarehouseStockDTOList = new ArrayList<>();

        SectionDTO sectionDTO = SectionDTO.builder().sectionId(1L).warehouseId(1L).build();
        BatchDTO batchDTO = BatchDTO.builder()
                .batchId(1L)
                .quantity(10)
                .expirationDate(LocalDate.parse("2023-01-30"))
                .build();

        batchDTOList.add(batchDTO);

         ProductWarehouseStockDTO productWarehouseStockDTO = ProductWarehouseStockDTO.builder()
                .sectionDTO(sectionDTO)
                .productId(5L)
                .batchDTO(batchDTOList)
                .build();

         productWarehouseStockDTOList.add(productWarehouseStockDTO);

        return productWarehouseStockDTOList;
    }

    public static ProductWarehouseDTO mockProductWarehouseDTO() {
        List<WarehouseStockDTO> WarehouseStockDTOList = new ArrayList<>();

        WarehouseStockDTO warehouseStockDTO = WarehouseStockDTO.builder()
                .warehouseCode(1L)
                .totalQuantity(String.valueOf(30))
                .build();

        WarehouseStockDTOList.add(warehouseStockDTO );

        return ProductWarehouseDTO.builder()
                .productId(1L)
                .warehouses(WarehouseStockDTOList)
                .build();

    }
}
