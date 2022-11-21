package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.BatchDueDateDTO;
import com.group03.desafio_integrador.dto.BatchDueDateStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.repository.BatchRepository;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.group03.desafio_integrador.repository.InboundOrderRepository;
import com.group03.desafio_integrador.repository.ProductAdvertisingRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @InjectMocks
    private BatchService batchService;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @Mock
    ProductAdvertisingRepository productRepository;

    private Batch mockBatch;
    private Batch mockCreateBatch;
    private ProductAdvertising mockProductAdvertising;
    private List<Batch> mockBatchList = new ArrayList<>();
    private BatchDueDateDTO mockBatchDueDateDTO;
    private InboundOrder mockInboundOrder;
    private List<InboundOrder> mockInboundOrderList = new ArrayList<>();
    private BatchDueDateStockDTO mockbatchDueDateStockDTO;
    private List<ProductAdvertising> mockProductListFresh;

    @BeforeEach
    void setUp() {
        mockBatch = TestsMocks.mockBatch();
        mockCreateBatch = TestsMocks.createBatch();
        mockBatchList.add(mockBatch);
        mockProductAdvertising = TestsMocks.mockProductAdvertising();
        mockBatchDueDateDTO = TestsMocks.mockBatchDueDateDTO();
        mockInboundOrder = TestsMocks.mockInboundOrder();
        mockInboundOrderList.add(mockInboundOrder);
        mockbatchDueDateStockDTO = TestsMocks.mockbatchDueDateStockDTO();
        mockProductListFresh = TestsMocks.mockProductListFresh();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById_returnSuccess_whenBatchExists() {
        BDDMockito.when(batchRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.ofNullable(mockBatch));

        Batch batchById = batchService.getById(1L);

        assertThat(batchById).isNotNull();
        assertThat(batchById).isEqualTo(mockBatch);
    }

    @Test
    void save_returnNewBatch_whenValidData() {
        BDDMockito.when(batchRepository.save(ArgumentMatchers.any(Batch.class))).thenReturn(mockBatch);
        Batch newBatch = batchService.save(mockCreateBatch);

        assertThat(newBatch).isNotNull();
        assertThat(newBatch.getBatchId()).isEqualTo(mockBatch.getBatchId());
        assertThat(newBatch.getProductQuantity()).isPositive();
        assertThat(newBatch.getVolume()).isPositive();
        assertThat(newBatch.getPrice()).isPositive();
        assertThat(newBatch).isEqualTo(mockBatch);
    }

    @Test
    void getById_returnRunTimeException_whenBatchIdNotFound() {
        BDDMockito.when(batchRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> batchService.getById(1L));

        assertThat(notFoundException.getMessage()).isEqualTo("Batch not found!");
    }

    @Test
    void findBatchByProductId_returnSuccess_whenFindProductIdInBatch() {
        BDDMockito.when(batchRepository.findAllByProductId(ArgumentMatchers.any(ProductAdvertising.class)))
                .thenReturn(mockBatchList);

        List<Batch> batchByProductId = batchService.findBatchByProductId(mockProductAdvertising);

        assertThat(batchByProductId).isNotNull();
        assertThat(batchByProductId).isEqualTo(mockBatchList);
    }

    @Test
    void getAllDueDate_returnBatchDueDateStockDTO_whenDataIsNotEmpty() {
        BDDMockito.when(batchRepository.findAllByExpirationDateGreaterThan(ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(mockBatchList);

        BDDMockito.when(inboundOrderRepository.findAll())
                .thenReturn(mockInboundOrderList);

        BatchDueDateStockDTO batchDueDateStockDTO = batchService.getAllDueDate(1, "1");

        assertThat(batchDueDateStockDTO).isNotNull();
        assertThat(batchDueDateStockDTO).isInstanceOf(BatchDueDateStockDTO.class);
    }

    @Test
    void getAllDueDateCategory_returnBatchDueDateStockDTO_whenDataIsNotEmpty() {
        BDDMockito.when(batchRepository.findAllByExpirationDateGreaterThan(ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(mockBatchList);
        BDDMockito.when(productRepository.findAllByCategory(ArgumentMatchers.any(CategoryEnum.class)))
                .thenReturn(mockProductListFresh);

        BatchDueDateStockDTO batchDueDateStockDTO = batchService.getAllDueDateCategory(1, "FS", "asc");

        assertThat(batchDueDateStockDTO).isNotNull();
        assertThat(batchDueDateStockDTO).isInstanceOf(BatchDueDateStockDTO.class);
    }

}