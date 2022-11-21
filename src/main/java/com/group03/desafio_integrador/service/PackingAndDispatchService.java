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
    private BuyerRepository buyerRepository;

    /**
     * Método responsável por retornar todos os carrinhos de compra.
     * @return Retorna uma lista do tipo CartProduct
     * @author Ingrid Paulino
     */
    @Override
    public List<CartProduct> findAllCartProduct() {
        return cartProductRepository.findAll();
    }

    /**
     * Método responsável por retornar os carrinhos de compra com status de finalizado - coleta
     * @return Retorna uma lista do tipo PackingOrderDTO
     * @author Ingrid Paulino
     */
    @Override
    public List<PackingOrderDTO> getAllFinishedPurchases() {
        List<PackingOrderDTO> packingOrderFinishidDTOS = new ArrayList<>();
        List<CartProduct> cartBuyerOfProducts = cartProductRepository.findAll();

        return cartBuyerOfProducts.stream().map(cartProduct -> {
            if (cartProduct.getShoppingCart().getOrderStatus().equals(OrderStatusEnum.FINALIZADO)) {
                PackingOrderDTO packing = PackingOrderDTO.builder()
                        .cart_product_id(cartProduct.getCartProductId())
                        .product_id(cartProduct.getCartProductId())
                        .buyer_id(cartProduct.getShoppingCart().getBuyer().getBuyerId())
                        .category(cartProduct.getProductAdvertising().getCategory())
                        .order_status(String.valueOf(cartProduct.getShoppingCart().getOrderStatus()))
                        .shopping_cart(cartProduct.getShoppingCart())
                        .build();

                packingOrderFinishidDTOS.add(packing);
            }
            return packingOrderFinishidDTOS;
        }).collect(Collectors.toList()).get(0);
    }

    /**
     * Método responsável por deletar os carrinhos de compra que foram salvos/registrados na tabela de embalagem
     * @author Ingrid Paulino
     */
    @Override
    public void deleteAllCartProductFinished() {
        List<PackingOrderDTO> cartProductOrderFinished = getAllFinishedPurchases();
        cartProductOrderFinished.forEach(cartProduct -> cartProductRepository.deleteById(cartProduct.getCart_product_id()));
    }

    /**
     * Método responsável por salvar todas as compras finalizadas na tabela de embalagem
     * @author Ingrid Paulino
     */
    @Override
    public void saveFinishedPurchases() {
        List<PackingOrderDTO> cartProductOrderFinished = getAllFinishedPurchases();
        cartProductOrderFinished.forEach(packing -> {
            DispatchPacking savePackingInBanco = DispatchPacking.builder()
                    .product_id(packing.getProduct_id())
                    .buyer_id(packing.getBuyer_id())
                    .category(packing.getCategory())
                    .build();

            packingRepository.save(savePackingInBanco);
        });
        deleteAllCartProductFinished();
    }

    /**
     * Método responsável por deletar os carrinhos registrados na tabela de embalagem.
     * @author Ingrid Paulino
     */
    @Override
    public void deleteAllCartProductEmbalados() {
        List<DispatchPacking> dispatchPackings = packingRepository.findAll();
        dispatchPackings.forEach(packaged -> packingRepository.deleteById(packaged.getId_Packing()));
    }

    /**
     * Método responsável por empacotar na mesma embalagem os produtos com categoria e comprador iguais e salvar esses dados na tabela de despacho
     * @author Ingrid Paulino
     */
    @Override
    public void packagedProductsFromSameBuyerAndCategory() {
        List<PackingOrder> dispatch = packingRepository.packingByDispatch();

        for(PackingOrder element : dispatch) {
            Buyer buyer = buyerRepository.findById(element.getBuyer_id()).orElseThrow(() -> new NotFoundException("not found id") );
            Dispatch savePacking = Dispatch.builder()
                    .buyer(buyer)
                    .category(element.getCategory())
                    .status(DispatchStatusEnum.ENVIADO)
                    .build();

            dispatchRepository.save(savePacking);
        }
        deleteAllCartProductEmbalados();
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
     * Método responsável por atualizar status da entrega para entregue e encaminhar um email avisando para o comprador que o produto foi entrgue.
     * @author Ingrid Paulino
     * @param id -Long
     * @return Retorna uma entidade do tipo Dispatch ou uma string caso o pacote não sejá encontrado
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public Dispatch updateStatusDispatch(Long id) throws NotFoundException {
        Optional<Dispatch> packingExist = Optional.ofNullable(dispatchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado! ")));

        packingExist.ifPresent(dispatch -> dispatch.setStatus(DispatchStatusEnum.ENTREGUE));

        if(packingExist.get().getStatus() == DispatchStatusEnum.ENTREGUE) {
         JavaMailApp.sendMail(packingExist.get().getBuyer().getBuyerName(),
                 packingExist.get().getBuyer().getEmail(), packingExist.get().getBuyer().getAddress());
        }
        return dispatchRepository.save(packingExist.get());
    }

    /**
     * Rota responsável por deletar as entregas com status de entrgue.
     * @author Ingrid Paulino
     * @return Retorna uma string.
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