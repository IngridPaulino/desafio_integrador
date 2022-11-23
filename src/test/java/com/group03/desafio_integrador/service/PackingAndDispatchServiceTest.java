package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.PackingOrder;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.Buyer;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.Dispatch;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.entities.entities_enum.DispatchStatusEnum;
import com.group03.desafio_integrador.repository.BuyerRepository;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PackingAndDispatchServiceTest {
    @InjectMocks
    private PackingAndDispatchService packingAndDispatchService;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private DispatchPackingRepository packingRepository;

    @Mock
    private DispatchRepository dispatchRepositories;

    @Mock
    private BuyerRepository buyerRepository;

    private List<CartProduct>  mockcartproductList;
    private List<Dispatch> mockDispatch;
    private List<DispatchPacking> mockDispatchPacking;
    private Dispatch mockDispatchPacking2;
    private PackingOrder mockDispatchPackingOrder;
    private Buyer mockBuyer;


    @BeforeEach
    void setUp() {
        mockcartproductList = MocksReq6Ingrid.mockcartproductList();
        mockDispatch = MocksReq6Ingrid.mockDispatch();
        mockDispatchPacking = MocksReq6Ingrid.mockDispatchPacking();
        mockDispatchPacking2 = MocksReq6Ingrid.mockDispatchPacking2();
      //  mockDispatchPackingOrder = (PackingOrder) MocksReq6Ingrid.mockDispatchPackingOrder();
        mockBuyer = MocksReq6Ingrid.mockBuyer();
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

    @Test
    void deleteAllCartProductFinished() {
        BDDMockito.when(cartProductRepository.findAll())
                .thenReturn(mockcartproductList);

        Object response = packingAndDispatchService.deleteAllCartProductFinished();
        assertThat(response).isNull();
    }

    @Test
    void saveFinishedPurchases() {
        BDDMockito.when(cartProductRepository.findAll())
                .thenReturn(mockcartproductList);

        List<DispatchPacking> dispatchPacking = packingAndDispatchService.saveFinishedPurchases();
        assertThat(dispatchPacking).isNotNull();
        assertThat(dispatchPacking).asList();
    }

    @Test
    void deleteAllCartProductEmbalados() {
        BDDMockito.when(packingRepository.findAll())
                .thenReturn(mockDispatchPacking);

        Object response = packingAndDispatchService.deleteAllCartProductEmbalados();
        assertThat(response).isNull();
    }


   // @Test
   // void packagedProductsFromSameBuyerAndCategory() {
   //     BDDMockito.when(packingRepository.packingByDispatch())
    //            .thenReturn((List<PackingOrder>) mockDispatchPackingOrder);

     //   BDDMockito.when(buyerRepository.findById(ArgumentMatchers.any(Long.class)))
     //           .thenReturn(Optional.ofNullable(mockBuyer));

     //   BDDMockito.when(packingRepository.save(ArgumentMatchers.any(DispatchPacking.class)))
      //          .thenReturn((DispatchPacking) mockDispatchPacking);

      //  List<DispatchPacking> dispatchPacking = packingAndDispatchService.saveFinishedPurchases();

      //  assertThat(dispatchPacking).isNotNull();
    //}

   // @Test
   // void packagedProductsFromSameBuyerAndCategoryFail() throws NotFoundException{
        //BDDMockito.when(packingRepository.packingByDispatch())
          //      .thenReturn((List<PackingOrder>) mockDispatchPackingOrder);

      //  BDDMockito.when(buyerRepository.findById(ArgumentMatchers.any(Long.class)))
       //         .thenReturn(Optional.empty());

       // NotFoundException notFoundExceptionjq = assertThrows(NotFoundException.class, () -> packingAndDispatchService.packagedProductsFromSameBuyerAndCategory());
      //  assertThat(notFoundException.getMessage()).isEqualTo("not found id");
    //}

    @Test
    void getAllPackingForDispatch() {
        BDDMockito.when(dispatchRepositories.findAll())
                .thenReturn(mockDispatch);

        List<Dispatch> dispatches = packingAndDispatchService.getAllPackingForDispatch();
        assertThat(dispatches).asList();
    }

    @Test
    void updateStatusDispatch() {
        BDDMockito.when(dispatchRepositories.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.ofNullable(mockDispatchPacking2));

        BDDMockito.when(dispatchRepositories.save(ArgumentMatchers.any(Dispatch.class)))
                .thenReturn(mockDispatchPacking2);

        Dispatch dispatch = packingAndDispatchService.updateStatusDispatch(1L);

        assertThat(dispatch).isNotNull();
        assertThat(dispatch.getStatus()).isEqualTo(DispatchStatusEnum.ENTREGUE);
    }

    @Test
    void updateStatusDispatchFail() throws NotFoundException{
        BDDMockito.when(dispatchRepositories.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> packingAndDispatchService.updateStatusDispatch(99L));
        assertThat(notFoundException.getMessage()).isEqualTo("Produto não encontrado! ");
        //assertThat(dispatch.getStatus()).isEqualTo(DispatchStatusEnum.ENTREGUE);
    }

    @Test
    void deleteAllPackingsEntregue() {
        AtomicReference<Integer> delete = packingAndDispatchService.deleteAllPackingsEntregue();
        assertThat(delete).isNotNull();
        ;    }

}