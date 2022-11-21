package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.dto.PackingOrder;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.repository.CartProductRepository;
import com.group03.desafio_integrador.repository.DispatchPackingRepository;
import com.group03.desafio_integrador.repository.DispatchRepository;
import com.group03.desafio_integrador.utils.mocks.MocksReq6Ingrid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PackingAndDispatchServiceTest {
    @InjectMocks
    private PackingAndDispatchService packingAndDispatchService;

    @Mock
    private CartProductRepository cartProductRepository;

    @Autowired
    private DispatchPackingRepository packingRepository;

    @Mock
    private DispatchRepository dispatchRepositories;

    private List<CartProduct>  mockcartproductList;
    private List<Dispatch> mockDispatch;


    @BeforeEach
    void setUp() {
        mockcartproductList = MocksReq6Ingrid.mockcartproductList();
        mockDispatch = MocksReq6Ingrid.mockDispatch();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllCartProduct() {
        BDDMockito.when(cartProductRepository.findAll())
                .thenReturn(mockcartproductList);

        List<CartProduct> cartProductList = packingAndDispatchService.findAllCartProduct();
        assertThat(cartProductList).asList();
    }

    @Test
    void getAllFinishedPurchases() {
        BDDMockito.when(cartProductRepository.findAll())
                .thenReturn(mockcartproductList);

        List<PackingOrderDTO> packingOrderDTOS = packingAndDispatchService.getAllFinishedPurchases();
        assertThat(packingOrderDTOS).asList();
    }

    //@Test
   // void deleteAllCartProductFinished() {
        //BDDMockito.when(cartProductRepository.findAll())
                //.thenReturn(mockcartproductList);
     //   BDDMockito.doNothing().when(cartProductRepository)
    //            .deleteById(ArgumentMatchers.any(Long.class));
    //    packingAndDispatchService.deleteAllCartProductFinished();
   // }

    @Test
    void saveFinishedPurchases() {
        BDDMockito.doNothing().when(packingRepository)
                .save(ArgumentMatchers.any(DispatchPacking.class));
        packingAndDispatchService.saveFinishedPurchases();
    }

    //@Test
   // void deleteAllPackingsEntregue() {
  //      BDDMockito.doNothing().when(packingRepository)
   //             .findAll();
   //     BDDMockito.doNothing().when(packingRepository)
   //             .save(ArgumentMatchers.any(DispatchPacking.class));
    //    packingAndDispatchService.deleteAllCartProductEmbalados();
    //}

    @Test
    void packagedProductsFromSameBuyerAndCategory() {
        //BDDMockito.when(packingRepository.packingByDispatch())
           //     .thenReturn(PackingOrder.class);

        //List<PackingOrderDTO> packingOrderDTOS = packingAndDispatchService.getAllFinishedPurchases();
        //assertThat(packingOrderDTOS).asList();
    }

    @Test
    void getAllPackingForDispatch() {
        BDDMockito.when(dispatchRepositories.findAll())
                .thenReturn(mockDispatch);

        List<Dispatch> dispatches = packingAndDispatchService.getAllPackingForDispatch();
        assertThat(dispatches).asList();
    }

    @Test
    void updateStatusDispatch() {
    }


    @Test
    void deleteAllCartProductEmbalados() {
    }
}