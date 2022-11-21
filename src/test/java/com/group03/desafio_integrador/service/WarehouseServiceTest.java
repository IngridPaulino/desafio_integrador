package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductWarehouseDTO;
import com.group03.desafio_integrador.dto.ProductWarehouseStockDTO;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.entities.Manager;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import com.group03.desafio_integrador.entities.Warehouse;
import com.group03.desafio_integrador.repository.InboundOrderRepository;
import com.group03.desafio_integrador.repository.ProductAdvertisingRepository;
import com.group03.desafio_integrador.repository.WarehouseRepository;
import com.group03.desafio_integrador.utils.mocks.TestsMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ProductAdvertisingRepository productRepository;

    @Mock
    private ProductAdvertisingService productService;

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @Mock
    private InboundOrderService inboundOrderService;

    private Warehouse mockWarehouse;
    private ProductAdvertising mockProductAdvertising;
    private List<InboundOrder> mockInboundOrderList;
    private ProductWarehouseDTO mockProductWarehouseDTO;

    @BeforeEach
    void setUp() {
        mockWarehouse = TestsMocks.mockWarehouse();
        mockProductAdvertising = TestsMocks.mockProductAdvertising();
        mockInboundOrderList = TestsMocks.mockCreateInboundOrderList();
        mockProductWarehouseDTO = TestsMocks.mockProductWarehouseDTO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById_returnSuccess_whenWarehouseExists() {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.ofNullable(mockWarehouse));

        Warehouse warehouseById = warehouseService.getById(1L);

        assertThat(warehouseById).isNotNull();
        assertThat(warehouseById).isEqualTo(mockWarehouse);
    }

    @Test
    void getById_returnRunTimeException_whenWarehouseIdNotFound() {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> warehouseService.getById(1L));

        assertThat(notFoundException.getMessage()).isEqualTo("Warehouse not found!");
    }

    @Test
    void getAll() {
        BDDMockito.when(warehouseRepository.findAll())
                .thenReturn(Collections.singletonList(mockWarehouse));

        List<Warehouse> warehouseList = warehouseService.getAll();

        assertThat(warehouseList).isNotNull();
        assertThat(warehouseList).isNotEmpty();
    }
}