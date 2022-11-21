package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotAcceptableException;
import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.BatchStockDTO;

import com.group03.desafio_integrador.dto.ProductWarehouseStockDTO;
import com.group03.desafio_integrador.dto.PurchaseOrderDTO;

import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.repository.*;
import com.group03.desafio_integrador.utils.mocks.TestsMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class InboundOrderServiceTest {

    @InjectMocks
    @Spy
    private InboundOrderService inboundOrderService;

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private BatchService batchService;

    @Mock
    private ProductAdvertisingService productAdvertisingService;

//    @Mock
//    private WarehouseService warehouseService;

    public final List<Batch> batchList = new ArrayList<>();
    private Batch mockBatch;
    private Batch mockUpdateBatch;
    private InboundOrder mockInboundOrder;
    private InboundOrder mockCreateInboundOrder;

    private List<InboundOrder> mockCreateInboundOrderList;
    private Warehouse mockWarehouse;
    private List<ProductWarehouseStockDTO> mockProductWarehouseStockDTOList;

    @BeforeEach
    void setUp() {

        mockBatch = TestsMocks.mockBatch();

        Batch mockBatchList = TestsMocks.createBatch();

        batchList.add(mockBatchList);

        mockUpdateBatch = TestsMocks.mockUpdateBatch();

        mockInboundOrder = TestsMocks.mockInboundOrder();

        mockCreateInboundOrder = TestsMocks.mockCreateInboundOrder();

        mockCreateInboundOrderList = TestsMocks.mockCreateInboundOrderList();

        mockProductWarehouseStockDTOList = TestsMocks.mockProductWarehouseStockDTOList();

        mockWarehouse = TestsMocks.mockWarehouse();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll_returnListInboundOrders_whenNotEmpty() {
        BDDMockito.when(inboundOrderRepository.findAll())
                .thenReturn(Collections.singletonList(mockInboundOrder));

        List<InboundOrder> inboundOrderList = inboundOrderService.getAll();

        assertThat(inboundOrderList).isNotNull();
        assertThat(inboundOrderList).isNotEmpty();
        assertThat(inboundOrderList.get(0)).isEqualTo(mockInboundOrder);
    }

    @Test
    void save_returnSuccess_whenValidData() throws Exception {
        BDDMockito.doNothing().when(inboundOrderService)
                .validateOrder(ArgumentMatchers.eq(mockCreateInboundOrder));

        BDDMockito.when(inboundOrderRepository.save(ArgumentMatchers.any(InboundOrder.class)))
                .thenReturn(mockInboundOrder);

        BatchStockDTO inboundResponse = inboundOrderService.save(mockCreateInboundOrder);

        BDDMockito.verify(inboundOrderService, BDDMockito.times(1))
                .validateOrder(ArgumentMatchers.eq(mockCreateInboundOrder));

        assertThat(inboundResponse).isNotNull();
        assertThat(inboundResponse.getBatchStock().containsAll(mockInboundOrder.getBatchList())).isTrue();
    }

//    @Test
//    void save_returnNotAcceptableException_whenInvalidData() throws Exception {
////        BDDMockito.doNothing().when(inboundOrderService)
////                .validateOrder(ArgumentMatchers.eq(TestsMocks.mockCreateErrorInboundOrder()));
//
//        BDDMockito.when(inboundOrderRepository.save(ArgumentMatchers.any(InboundOrder.class)))
//                .thenReturn(TestsMocks.mockCreateErrorInboundOrder());
//
//        inboundOrderService.save(TestsMocks.mockCreateErrorInboundOrder());
//
//        BDDMockito.verify(inboundOrderService, BDDMockito.times(1))
//                .validateOrder(ArgumentMatchers.eq(mockCreateInboundOrder));
//
//        NotAcceptableException notAcceptableException = assertThrows(NotAcceptableException.class, () -> inboundOrderService.validateOrder(TestsMocks.mockCreateErrorInboundOrder()));
//
//        assertThat(notAcceptableException.getMessage()).isEqualTo("Product " + TestsMocks.mockCreateErrorInboundOrder().getBatchList().get(0).getProductId().getProductId()  + " not belongs to this section");
//    }

    @Test
    void update_returnSuccess_whenValidData() {
        BDDMockito.when(batchService.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(mockBatch);

        batchService.getById(1L);

        BDDMockito.when(batchService.save(ArgumentMatchers.any(Batch.class)))
                .thenReturn(mockUpdateBatch);

        Batch updatedBatch = batchService.save(mockUpdateBatch);

        assertThat(updatedBatch).isNotNull();
        assertThat(updatedBatch.getVolume()).isEqualTo(mockUpdateBatch.getVolume());
        assertThat(updatedBatch.getProductQuantity()).isEqualTo(mockUpdateBatch.getProductQuantity());
        assertThat(updatedBatch.getPrice()).isEqualTo(mockUpdateBatch.getPrice());
    }

    @Test
    void getAllProductWarehouseStock() throws Exception {
        BDDMockito.when(inboundOrderService.getAll())
                .thenReturn(mockCreateInboundOrderList);

        BDDMockito.doNothing().when(inboundOrderService)
              .validateOrder(ArgumentMatchers.any(InboundOrder.class));

        List<ProductWarehouseStockDTO> productWarehouseStockDTOList = inboundOrderService.getAllProductWarehouseStock(1L);

        BDDMockito.verify(inboundOrderService, BDDMockito.times(1))
                .validateOrder(ArgumentMatchers.any(InboundOrder.class));

        assertThat(productWarehouseStockDTOList).isNotNull();
    }

    @Test
    void getAllOrdinancesForBatches() {
        List<ProductWarehouseStockDTO> productWarehouseStockDTOList = inboundOrderService.getAllOrdinancesForBatches(mockProductWarehouseStockDTOList, "L");

        assertThat(productWarehouseStockDTOList).isNotNull();
        assertThat(productWarehouseStockDTOList).asList();
    }
    
   @Test
    void validateOrder_doNotThrowError_whenValidData() throws Exception {
        doNothing().when(inboundOrderService)
                .validateWarehouse(ArgumentMatchers.any(Warehouse.class));
        doNothing().when(inboundOrderService)
                .validateProducts(ArgumentMatchers.anyList());
        doNothing().when(inboundOrderService)
                .validateSection(ArgumentMatchers.eq(mockCreateInboundOrder));

        inboundOrderService.validateOrder(mockCreateInboundOrder);

        verify(inboundOrderService, times(1))
                .validateWarehouse(ArgumentMatchers.any(Warehouse.class));

        verify(inboundOrderService, times(1))
                .validateProducts(ArgumentMatchers.anyList());

        verify(inboundOrderService, times(1))
                .validateSection(ArgumentMatchers.eq(mockCreateInboundOrder));
    }

    @Test
    void validateOrder_throwError_whenManagerInWarehouseNotFound() {
        BDDMockito.doReturn(new Warehouse(1L, 3000.0, null))
                .when(warehouseService)
                .getById(ArgumentMatchers.anyLong());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> inboundOrderService.validateOrder(mockCreateInboundOrder));

        assertThat(notFoundException.getMessage()).isEqualTo("Manager not found for this Warehouse!");
    }

    @Test
    void validateProducts_throwError_whenProductNotFound(){
        doThrow(new NotFoundException("")).when(productAdvertisingService)
                .getById(ArgumentMatchers.anyLong());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> inboundOrderService.validateProducts(batchList));

        assertThat(notFoundException.getMessage()).isEqualTo("Products not found");
    }
}