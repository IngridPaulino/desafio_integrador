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

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PackingAndDispatchService implements IPackingAndDispatchService {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private DispatchPackingRepository packingRepository;
    @Autowired
    private DispatchRepository dispatchRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public List<CartProduct> findAllCartProduct() {
        return cartProductRepository.findAll();
    }

    /**
     * Método responsável por retornar todas as compras com status de finalizado
     * @return Retorna uma lista do tipo PackingOrderDTO
     * @author Ingrid Paulino
     */
    @Override
    public List<PackingOrderDTO> getAllFinishedPurchases() {
        List<PackingOrderDTO> packingOrderFinishidDTOS = new ArrayList<>();
        List<CartProduct> cartBuyerOfProducts = cartProductRepository.findAll();

        return cartBuyerOfProducts.stream().map(cartProduct -> {
            //Buyer buyer = Buyer.builder()
                   // .buyerId(cartProduct.getShoppingCart().getBuyer().getBuyerId())
                   // .buyerName(cartProduct.getShoppingCart().getBuyer().getBuyerName())
                   // .email(cartProduct.getShoppingCart().getBuyer().getEmail())
                   // .address(cartProduct.getShoppingCart().getBuyer().getAddress())
                  //  .build();
            if (cartProduct.getShoppingCart().getOrderStatus().equals(OrderStatusEnum.FINALIZADO)) {
                PackingOrderDTO packing = PackingOrderDTO.builder()
                        .cart_product_id(cartProduct.getCartProductId())
                        .product_id(cartProduct.getCartProductId())
                        //.seller(cartProduct.getProductAdvertising().getSeller().getSellerId())
                        .buyer_id(cartProduct.getShoppingCart().getBuyer().getBuyerId())
                        //.email_buyer(cartProduct.getShoppingCart().getBuyer().getEmail())
                        //.address(cartProduct.getShoppingCart().getBuyer().getAddress())
                        .category(cartProduct.getProductAdvertising().getCategory())
                        .order_status(String.valueOf(cartProduct.getShoppingCart().getOrderStatus()))
                        .shopping_cart(cartProduct.getShoppingCart())
                        .build();

                packingOrderFinishidDTOS.add(packing);
            }
            return packingOrderFinishidDTOS;
        }).collect(Collectors.toList()).get(0);
    }

    @Override
    public void deleteAllCartProductFinished() {
        List<PackingOrderDTO> cartProductOrderFinished = getAllFinishedPurchases();
        //PackingOrderDTO packing = cartProductOrderFinished.get(0);
        cartProductOrderFinished.forEach(cartProduct -> cartProductRepository.deleteById(cartProduct.getCart_product_id()));
        //cartProductOrderFinished.forEach(cartProduct -> shoppingCartRepository.deleteById(packing.getShopping_cart().getShoppingCartId()));
        // cartProductOrderFinished.forEach(cartProduct -> cartProductRepository.deleteById(cartProduct.getShopping_cart().getShoppingCartId()));
    }

    /**
     * Método responsável por salvar todas as compras finalizadas na tabela de embalagem
     * @return Não tem retorno
     * @author Ingrid Paulino
     */
    @Override
    public List<DispatchPacking> saveFinishedPurchases() {
        List<DispatchPacking> dispatchPackings = new ArrayList<>();
        List<PackingOrderDTO> cartProductOrderFinished = getAllFinishedPurchases();
        cartProductOrderFinished.forEach(packing -> {
            DispatchPacking savePackingInBanco = DispatchPacking.builder()
                    .product_id(packing.getProduct_id())
                    .buyer_id(packing.getBuyer_id())
                    .category(packing.getCategory())
                    .build();

            dispatchPackings.add(savePackingInBanco);
            packingRepository.save(savePackingInBanco);
        });
        deleteAllCartProductFinished();
        return dispatchPackings;
    }

    @Override
    public void deleteAllCartProductEmbalados() {
        List<DispatchPacking> dispatchPackings = packingRepository.findAll();
        dispatchPackings.forEach(packaged -> packingRepository.deleteById(packaged.getId_Packing()));
    }

    /**
     * Método responsável por empacotar os produtos do mesmo comprador e categoria. E salvar esses dados na tabela de despacho
     * @return Não tem retorno
     * @author Ingrid Paulino
     */
    @Override
    public List<Dispatch> packagedProductsFromSameBuyerAndCategory() {
        List<Dispatch> a = new ArrayList<>();
        List<PackingOrder> dispatch = packingRepository.packingByDispatch();

        for(PackingOrder element : dispatch) {
            Buyer buyer = buyerRepository.findById(element.getBuyer_id()).orElseThrow(() -> new NotFoundException("not found id") );
            Dispatch savePacking = Dispatch.builder()
                    .buyer_id(buyer)
                    .category(element.getCategory())
                    .status(DispatchStatusEnum.ENVIADO)
                    .build();

            a.add(savePacking);
            dispatchRepository.save(savePacking);
        }
        deleteAllCartProductEmbalados();
        return a;
    }

    /**
     * Método responsável por retornar todos os pacotes que estão prontos para entrega
     * @return retorna uma lista de Dispatch
     * @author Ingrid Paulino
     */
    @Override
    public List<Dispatch> getAllPackingForDispatch() {
        return dispatchRepository.findAll();
    }

    /**
     * Método responsável por atualizar status de entrega e mandar notificação da entrega para o comprador e vendedor
     * @param id
     * @return Retorna o objeto do pacote entregue
     */
    @Override
    public Dispatch updateStatusDispatch(Long id) throws NotFoundException {
        Optional<Dispatch> packingExist = Optional.ofNullable(dispatchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id not found! ")));

        packingExist.ifPresent(dispatch -> dispatch.setStatus(DispatchStatusEnum.ENTREGUE));

        if(packingExist.get().getStatus() == DispatchStatusEnum.ENTREGUE) {
         JavaMailApp.sendMail(packingExist.get().getBuyer_id().getBuyerName(),
                 packingExist.get().getBuyer_id().getEmail(), packingExist.get().getBuyer_id().getAddress());
        }
        return dispatchRepository.save(packingExist.get());
    }

    /**
     * @return
     */
    @Override
    public AtomicReference<Integer> deleteAllPackingsEntregue() {
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        List<Dispatch> getAllPackingForDispatch = getAllPackingForDispatch();

        getAllPackingForDispatch.forEach(obj -> {
            if(obj.getStatus() == DispatchStatusEnum.ENTREGUE) {
                dispatchRepository.deleteById(obj.getId_dispatch());
            } else {
                sum.updateAndGet(v -> v + 1);
            }
        });

        return sum;
    }
}
