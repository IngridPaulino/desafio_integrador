package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.ValidationErrorDetail;
import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductDTO;
import com.group03.desafio_integrador.dto.PurchaseOrderDTO;
import com.group03.desafio_integrador.dto.ShoppingCartTotalDTO;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.repository.ProductAdvertisingRepository;
import com.group03.desafio_integrador.utils.mocks.TestsMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;


import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductAdvertisingServiceTest {

    @InjectMocks
    @Spy
    private ProductAdvertisingService productAdvertisingService;

    @Mock
    private BuyerService buyerService;

    @Mock
    private ProductAdvertisingRepository productAdvertisingRepository;

    @Mock
    private BatchService batchService;

    private ProductAdvertising mockProductAdvertising;

    private List<ProductAdvertising> mockProductList;

    private List<ProductAdvertising> mockProductListFresh;

    private List<ProductAdvertising> mockProductListFreshEmpty;

    private PurchaseOrderDTO mockCreateCartRequest;

    @BeforeEach
    void setUp() {
        mockProductAdvertising = TestsMocks.mockProductAdvertising();
        mockProductList = TestsMocks.mockProductList();
        mockProductListFresh = TestsMocks.mockProductListFresh();
        mockProductListFreshEmpty = TestsMocks.mockProductListFreshEmpty();
        mockCreateCartRequest = TestsMocks.mockCreateCartRequest();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById_returnSuccess_whenProductAdvertisingExists() {
        when(productAdvertisingRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.ofNullable(mockProductAdvertising));
        ProductAdvertising productById = productAdvertisingService.getById(1L);

        assertThat(productById).isNotNull();
        assertThat(productById).isEqualTo(mockProductAdvertising);
    }

//    @Test
//    void getById_returnNotFoundException_whenProductAdvertisingIsEmpty() throws NotFoundException{
//        when(productAdvertisingRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());
//
//        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productAdvertisingService.getById(1L));
//        assertThat(notFoundException.getMessage()).isEqualTo("Product not found!");
//    }

    @Test
    void getAll_returnSuccess_whenProductAdvertisingListExists() {
        when(productAdvertisingRepository.findAll()).thenReturn(mockProductList);
        List<ProductAdvertising> listProductAdvertising = productAdvertisingService.getAll();

        assertThat(listProductAdvertising).asList();
        assertThat(listProductAdvertising).isNotNull();
        assertThat(listProductAdvertising).isNotEmpty();
        assertThat(listProductAdvertising.get(0)).isEqualTo(mockProductList.get(0));
    }

    @Test
    void getAll_returnNotFoundException_whenProductAdvertisingListIsEmpty() throws NotFoundException{
        when(productAdvertisingRepository.findAll()).thenReturn(List.of());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productAdvertisingService.getAll());
        assertThat(notFoundException.getMessage()).isEqualTo("Product Advertising not found");
    }

    @Test
    void getAllByCategory_returnSuccess_whenProductAdvertisingCategoryListExists() {
        when(productAdvertisingRepository.findAllByCategory(ArgumentMatchers.any(CategoryEnum.class))).thenReturn(mockProductListFresh);
        List<ProductAdvertising> listProductAdvertisingFresh = productAdvertisingService.getAllByCategory(String.valueOf(CategoryEnum.FS));

        assertThat(listProductAdvertisingFresh).isNotNull();
        assertThat(listProductAdvertisingFresh).isNotEmpty();
        assertThat(listProductAdvertisingFresh.get(0)).isEqualTo(mockProductListFresh.get(0));
    }

    @Test
    void getAllByCategory_returnNotFoundException_whenProductAdvertisingCategoryListIsEmpty() throws NotFoundException{
        when(productAdvertisingRepository.findAllByCategory(ArgumentMatchers.any(CategoryEnum.class))).thenReturn(mockProductListFreshEmpty);

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productAdvertisingService.getAllByCategory(String.valueOf(CategoryEnum.FS)));
        assertThat(notFoundException.getMessage()).isEqualTo("Category not found");
    }

    @Test
    void registerOrder_returnSuccess_whenIsRegisterOneOrder() throws Exception {

        doNothing().when(productAdvertisingService)
                .verifyStock(ArgumentMatchers.eq(mockCreateCartRequest));

        doNothing().when(productAdvertisingService)
                .saveShoppingCart(ArgumentMatchers.anySet(), ArgumentMatchers.any(ShoppingCart.class));

        doReturn(mockProductAdvertising).when(productAdvertisingService)
                .getById(ArgumentMatchers.eq(mockCreateCartRequest.getProducts().get(0).getProductId()));

        ShoppingCartTotalDTO registerOrderResponse =  productAdvertisingService.registerOrder(mockCreateCartRequest);

        buyerService.getById(mockCreateCartRequest.getProducts().get(0).getProductId());

        verify(productAdvertisingService, times(1))
                .verifyStock(ArgumentMatchers.any(PurchaseOrderDTO.class));

        assertThat(registerOrderResponse).isNotNull();
        assertThat(registerOrderResponse.getTotalPrice()).isEqualTo(TestsMocks.mockCreateCartResponse().getTotalPrice());
    }

//    @Test
//    void verifyStock_doNotThrowError_whenValidData() {
//
//        doNothing().when(productAdvertisingService)
//                .verifyProductExists(ArgumentMatchers.anyList(), ArgumentMatchers.anyLong());
//        doNothing().when(productAdvertisingService)
//                .verifyProductExpirationDate(ArgumentMatchers.anyList(), ArgumentMatchers.any(Batch.class), ArgumentMatchers.anyLong());
//        doNothing().when(productAdvertisingService)
//                .verifyProductStockQuantity(ArgumentMatchers.anyList(), ArgumentMatchers.any(ProductDTO.class), ArgumentMatchers.any(Batch.class), ArgumentMatchers.anyLong());
//
//        doReturn(List.of(TestsMocks.mockBatch())).when(batchService)
//                .findBatchByProductId(ArgumentMatchers.any(ProductAdvertising.class));
//
//        productAdvertisingService.verifyStock(mockCreateCartRequest);
//
//        verify(productAdvertisingService, times(1))
//                .verifyProductExpirationDate(ArgumentMatchers.anyList(), ArgumentMatchers.any(Batch.class), ArgumentMatchers.anyLong());
//        verify(productAdvertisingService, times(1))
//                .verifyProductStockQuantity(ArgumentMatchers.anyList(), ArgumentMatchers.any(ProductDTO.class), ArgumentMatchers.any(Batch.class), ArgumentMatchers.anyLong());
//    }

    @Test
    void verifyStock_throwError_whenBatchNotFound() {
        doReturn(List.of()).when(batchService)
                .findBatchByProductId(ArgumentMatchers.any(ProductAdvertising.class));

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productAdvertisingService.verifyStock(mockCreateCartRequest));

        assertThat(notFoundException.getMessage()).isEqualTo("No batch found with this product!");
    }

//    @Test
//    void verifyStock_throwError_whenProductsNotFound() {
//
//        var errorDetail = new ValidationErrorDetail("field", "message", "rejectedValue");
//        doAnswer(invocation -> {
//            List<ValidationErrorDetail> errorDetails = invocation.getArgument(0);
//            errorDetails.add(errorDetail);
//            return null;
//        }).when(productAdvertisingService)
//                .verifyProductExists(ArgumentMatchers.anyList(), ArgumentMatchers.anyLong());
//
//        doReturn(List.of(TestsMocks.mockBatch())).when(batchService)
//                .findBatchByProductId(ArgumentMatchers.any(ProductAdvertising.class));
//
//        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productAdvertisingService.verifyStock(mockCreateCartRequest));
//
//        assertThat(notFoundException.getMessage()).isEqualTo("Products not found");
//        assertThat(notFoundException.getErrors().get(0)).isEqualTo(errorDetail);
//
//        verify(productAdvertisingService, times(1))
//                .verifyProductExists(ArgumentMatchers.anyList(), ArgumentMatchers.anyLong());
//    }
}