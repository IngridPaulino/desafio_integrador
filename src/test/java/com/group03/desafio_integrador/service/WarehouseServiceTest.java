package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.Manager;
import com.group03.desafio_integrador.entities.Warehouse;
import com.group03.desafio_integrador.repository.BatchRepository;
import com.group03.desafio_integrador.repository.WarehouseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    private Warehouse mockWarehouse;

    @BeforeEach
    void setUp() {
        mockWarehouse = new Warehouse(1L, 10000.00, Manager.builder().managerId(1L).build());
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
}