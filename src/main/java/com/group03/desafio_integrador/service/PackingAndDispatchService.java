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
    private CartProductRepository cartProductRepository;
    @Autowired
    private DispatchPackingRepository packingRepository;
    @Autowired
    private DispatchRepository dispatchRepository;

    /**
     * Método responsável por retornar todas as compras com status de finalizado
     *
     * @return Retorna uma lista do tipo PackingOrderDTO
     * @author Ingrid Paulino
     */
    @Override
    public List<PackingOrderDTO> getAllFinishedPurchases() {
        List<PackingOrderDTO> packingOrderFinishidDTOS = new ArrayList<>();
        List<CartProduct> cartBuyerOfProducts = cartProductRepository.findAll();

        return cartBuyerOfProducts.stream().map(cartProduct -> {
            Buyer buyer = Buyer.builder()
                    .buyerId(cartProduct.getShoppingCart().getBuyer().getBuyerId())
                    .buyerName(cartProduct.getShoppingCart().getBuyer().getBuyerName())
                    .build();
            if (cartProduct.getShoppingCart().getOrderStatus().equals(OrderStatusEnum.FINALIZADO)) {
                PackingOrderDTO packing = PackingOrderDTO.builder()
                        .cart_product_id(cartProduct.getCartProductId())
                        .product_id(cartProduct.getCartProductId())
                        .seller(cartProduct.getProductAdvertising().getSeller().getSellerId())
                        .buyer(buyer)
                        .category(cartProduct.getProductAdvertising().getCategory())
                        .order_status(String.valueOf(cartProduct.getShoppingCart().getOrderStatus()))
                        .build();

                packingOrderFinishidDTOS.add(packing);
            }
            return packingOrderFinishidDTOS;
        }).collect(Collectors.toList()).get(0);
    }

    /**
     * Método responsável por salvar todas as compras finalizadas na tabela de embalagem
     *
     * @return Retorna uma lista do tipo PackingOrderDTO
     * @author Ingrid Paulino
     */
    @Override
    public List<DispatchPacking> saveFinishedPurchases() {
        List<PackingOrderDTO> cartProductOrderFinished = getAllFinishedPurchases();

        return cartProductOrderFinished.stream().map(packing -> {
            DispatchPacking savePackingInBanco = DispatchPacking.builder()
                    .buyer_id(packing.getBuyer().getBuyerId())
                    .buyer_Name(packing.getBuyer().getBuyerName())
                    .category(packing.getCategory())
                    .status(DispatchStatusEnum.ABERTO)
                    .build();

            return packingRepository.save(savePackingInBanco);
        }).collect(Collectors.toList());
    }

    /**
     * Método responsável por empacotar os produtos do mesmo comprador e categoria. E salvar esses dados
     * na tabela de despacho
     *
     * @return Não tem retorno
     * @author Ingrid Paulino
     */

    @Override
    public void packagedProductsFromSameBuyerAndCategory() {
        List<PackingOrderDTO> dispatch = packingRepository.packingByDispatch();
        for(PackingOrderDTO element : dispatch) {
            Dispatch savePacking = Dispatch.builder()
                    .buyer_id(element.getBuyer().getBuyerId())
                    .buyer_Name(element.getBuyer().getBuyerName())
                    .category(element.getCategory())
                    .status(DispatchStatusEnum.ABERTO)
                    .build();

            dispatchRepository.save(savePacking);
        }
    }

    /**
     * Método responsável por retornar todos os pacotes que precisa ser entregues
     *
     * @return retorna uma lista de Dispatch
     * @author Ingrid Paulino
     */
    @Override
    public List<Dispatch> getAllPackingForDispatch() {
        return dispatchRepository.findAll();
    }

    /**
     * Método responsável por atualizar status de entrega em ABERTO ou ENTREGUE e mandar a notificação
     * da entrega entrege para o comrador e vendedor
     *
     * @param dispatchPacking
     * @return Retorna o objeto do pacote entregue
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
