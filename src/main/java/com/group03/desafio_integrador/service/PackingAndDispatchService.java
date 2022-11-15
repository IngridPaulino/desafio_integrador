package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.DispatchDTO;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.entities.entities_enum.DispatchStatusEnum;
import com.group03.desafio_integrador.entities.entities_enum.OrderStatusEnum;
import com.group03.desafio_integrador.repository.CartProductRepository;
import com.group03.desafio_integrador.repository.DispatchPackingRepository;
import com.group03.desafio_integrador.service.interfaces.IPackingAndDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackingAndDispatchService implements IPackingAndDispatchService {
    @Autowired
    private CartProductRepository repository;

    @Autowired
    private DispatchPackingRepository dispatchPackingRepository;

    private List<CartProduct> cartBuyerOfProducts;


    /**
     * @return
     */
    @Override
    public List<PackingOrderDTO> getAllCartProductFinished() {
        List<PackingOrderDTO> ordersFinishidDTOS = new ArrayList<>();
        cartBuyerOfProducts = repository.findAll();

        return cartBuyerOfProducts.stream().map(p -> {
            if (p.getShoppingCart().getOrderStatus().equals(OrderStatusEnum.FINALIZADO)) {
                PackingOrderDTO packing = PackingOrderDTO.builder()
                        .cart_product_id(p.getCartProductId())
                        .product_id(p.getCartProductId())
                        .order_status(String.valueOf(p.getShoppingCart().getOrderStatus()))
                        .buyer_id(p.getShoppingCart().getBuyer().getBuyerId())
                        .type(p.getProductAdvertising().getCategory())
                        .build();

                ordersFinishidDTOS.add(packing);
            }
            return ordersFinishidDTOS;
        }).collect(Collectors.toList()).get(0);
    }

    private Integer createId() {
        Integer id = 0;
        return id += 1;
    }

    @Override
    public void saveData() {
        List<PackingOrderDTO> cartProductOrderFinished = getAllCartProductFinished();

        cartProductOrderFinished.forEach(packing -> {
            DispatchPacking savePackingInBanco = DispatchPacking.builder()
                    .buyer_id(packing.getBuyer_id())
                    .category(packing.getType())
                    .status(DispatchStatusEnum.ABERTO)
                    .build();


            dispatchPackingRepository.save(savePackingInBanco);
        });
    }

    /**
     * @param dispatchPacking
     * @return
     */
    // TODO: 15/11/22 mudar a rota para path
    @Override
    public DispatchPacking updateStatusDispatch(DispatchPacking dispatchPacking) {
        Optional<DispatchPacking> packingExist = Optional.ofNullable(dispatchPackingRepository.findById(dispatchPacking.getId_Packing())
                .orElseThrow(() -> new NotFoundException("Id not found! ")));
        return dispatchPackingRepository.save(dispatchPacking);
    }


    /**
     * @return
     */
    @Override
    public Set<DispatchPacking> getAllPackingForDispatch() {
        List<DispatchPacking> cartProductOrderFinished = dispatchPackingRepository.findAll();
        Set<DispatchPacking> data = new LinkedHashSet<>();


        cartProductOrderFinished.forEach(f -> {
            data.add(f); //isso nn funciona
        });
        return data;
//

//        List<PackingOrderDTO> cartProductOrderFinished = getAllCartProductFinished();
//        HashSet<DispatchDTO> data = new LinkedHashSet<>();
//
//        cartProductOrderFinished.stream().map(f -> {
//            DispatchDTO dispatchDTO = DispatchDTO.builder()
//                    .id_Packing(Long.valueOf(createId()))
//                    .buyer_id(f.getBuyer_id())
//                    .category(f.getType())
//                    .status(DispatchStatusEnum.ABERTO)
//                    .build();
//
//            data.add(dispatchDTO );
//
//            return dispatchDTO ;
//        }).collect(Collectors.toList());
//
//        return data;
    }

    /**
     * @param id
     * @return
     */
    //@Override
    //public DispatchDTO updateStatusDispatch(DispatchDTO dispatch) {
     //   Set<DispatchDTO> packingsForDispatch = getAllPackingForDispatch();

     //   List<DispatchDTO> packingOpen = packingsForDispatch.stream()
      //          .filter(packing -> packing.getId_Packing() == dispatch.getId_Packing()).collect(Collectors.toList());

      // if (packingOpen.isEmpty())
       //     throw new NotFoundException("Packing Not Found");


       // packingOpen.get(0).setStatus(DispatchStatusEnum.FECHADO);

       // return packingOpen.get(0);
   // }
}
