package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.repository.CartProductRepository;
import com.group03.desafio_integrador.repository.DispatchRepository;
import com.group03.desafio_integrador.utils.mocks.MocksReq6Ingrid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PackingAndDispatchServiceTest {
    @InjectMocks
    private PackingAndDispatchService packingAndDispatchService;

    @Mock
    private CartProductRepository cartProductRepository;

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
    void getAllFinishedPurchases() {
        BDDMockito.when(cartProductRepository.findAll())
                .thenReturn(mockcartproductList);

        List<PackingOrderDTO> packingOrderDTOS = packingAndDispatchService.getAllFinishedPurchases();
        assertThat(packingOrderDTOS).asList();
    }

    @Test
    void saveFinishedPurchases() {
    }

    @Test
    void packagedProductsFromSameBuyerAndCategory() {
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
    void deleteAllPackingsEntregue() {
    }
}