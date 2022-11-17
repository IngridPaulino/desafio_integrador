package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.*;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.entities.entities_enum.*;
import com.group03.desafio_integrador.repository.*;
import com.group03.desafio_integrador.service.interfaces.IPackingAndDispatchService;
import com.group03.desafio_integrador.utils.JavaMailApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackingAndDispatchService implements IPackingAndDispatchService {
    @Autowired
    private CartProductRepository repository;
    @Autowired
    private DispatchPackingRepository dispatchPackingRepository;
    @Autowired
    private DispatchRepository dispatchRepository;

    /**
     * @return
     */
    @Override
    public List<PackingOrderDTO> getAllCartProductFinished() {
        List<PackingOrderDTO> ordersFinishidDTOS = new ArrayList<>();
        List<CartProduct> cartBuyerOfProducts = repository.findAll();

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

    /**
     * @return
     */
    @Override
    public List<Dispatch> getAllPackingForDispatch() {
        return dispatchRepository.findAll();
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

    @Override
    public List<DispatchDTO> packingsfinal() {
        List<DispatchDTO> dispatchDTOList = new ArrayList<>();
        List<Object[]> dispatch = dispatchPackingRepository.packingByDispatch();
        for(Object[] element : dispatch) {
            DispatchDTO a = DispatchDTO.builder()
                    .buyer_id((BigInteger) element[0])
                    .category((Integer) element[1])
                    .build();

            dispatchDTOList.add(a);
        }

        return dispatchDTOList;
    }

    @Override
    public void PackingProductsForDispatch() {
        List<DispatchDTO> s = packingsfinal();
        s.forEach(obj -> {
            Dispatch e = Dispatch.builder()
                    .buyer_id(obj.getBuyer_id())
                    .category(obj.getCategory())
                    .status(DispatchStatusEnum.ABERTO)
                    .build();

            dispatchRepository.save(e);
        });
    }

    /**
     * @param dispatchPacking
     * @return
     */
    // TODO: 15/11/22 mudar a rota para path
    @Override
    public Dispatch updateStatusDispatch(Dispatch dispatchPacking) {
        Optional<Dispatch> packingExist = Optional.ofNullable(dispatchRepository.findById(dispatchPacking.getId_Packing())
                .orElseThrow(() -> new NotFoundException("Id not found! ")));
        Dispatch updated = dispatchRepository.save(dispatchPacking);
       if(updated.getStatus() == DispatchStatusEnum.ENTREGUE) {
           JavaMailApp.sendMail();
        }
        return updated;
    }
}
