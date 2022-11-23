package com.group03.desafio_integrador.utils.mocks;

import com.group03.desafio_integrador.dto.PackingOrder;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.entities_enum.DispatchStatusEnum;
import com.group03.desafio_integrador.entities.entities_enum.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MocksReq6Ingrid {

    public static List<CartProduct> mockcartproductList() {
        List<CartProduct> cartProductList = new ArrayList<>();
        CartProduct cartProduct = new CartProduct(1L,
                ProductAdvertising.builder()
                        .productId(1L)
                        .fabricationDate(LocalDate.parse("2022-09-20"))
                        .fabricationTime(LocalDateTime.of(2022, 11, 9, 11, 43, 0))
                        .productName("Iogurte Natural")
                        .description("testes")
                        .seller(Seller.builder()
                                .sellerId(3L)
                                .sellerName("Seller 3")
                                .build())
                        .category(CategoryEnum.valueOf("RF"))
                        .productPrice(BigDecimal.valueOf(2.00))
                        .build(),
                ShoppingCart.builder()
                        .shoppingCartId(2L)
                        .date(LocalDate.parse("2022-11-15"))
                        .orderStatus(OrderStatusEnum.valueOf("ABERTO"))
                        .totalCartPrice(12.0)
                        .build(),
                2
        );
        cartProductList.add(cartProduct);

        return cartProductList;
    }

    public static List<Dispatch> mockDispatch() {
        List<Dispatch> dispatches = new ArrayList<>();
        Buyer buyer = Buyer.builder()
                .buyerId(1L)
                .buyerName("ingrid")
                .email("ingridpaulino1012@gmail.com")
                .address("Rua jose riveiro filho")
                .build();
        Dispatch dispatch = new Dispatch(1L,
                buyer,
                CategoryEnum.FS,
                DispatchStatusEnum.ABERTO
        );
        dispatches.add(dispatch);
        return dispatches;
    }

    public static List<DispatchPacking> mockDispatchPacking() {
        List<DispatchPacking> dispatchPackingList = new ArrayList<>();
        DispatchPacking dispatchPacking = DispatchPacking.builder()
                .id_Packing(1L)
                .product_id(4L)
                .buyer_id(1L)
                .category(CategoryEnum.FS)
                .build();

        dispatchPackingList.add(dispatchPacking);
        return dispatchPackingList;
    }

    public static Dispatch mockDispatchPacking2() {

        return Dispatch.builder()
                .id_dispatch(1L)
                .buyer(Buyer.builder()
                        .buyerId(1L)
                        .buyerName("Ingrid")
                        .email("xxxxxx@gmail.com")
                        .address("Rua X")
                        .build())
                .category(CategoryEnum.FS)
                .status(DispatchStatusEnum.ENVIADO)
                .build();
    }

    ////public static List<PackingOrder> mockDispatchPackingOrder() {
       // List<PackingOrder> packingOrderList = new ArrayList<>();

       // PackingOrder packingOrder = (PackingOrder) PackingOrderDTO.builder()
        //        .buyer_id(1L)
         //       .category(CategoryEnum.FS)
           //     .build();

        //packingOrderList.add(packingOrder);
       // return packingOrderList;
   // }

    public static Buyer mockBuyer() {
        return Buyer.builder()
                .buyerId(1L)
                .buyerName("ingrid")
                .email("ingridpaulino1012@gmail.com")
                .address("Rua jose riveiro filho")
                .build();
    }




}
