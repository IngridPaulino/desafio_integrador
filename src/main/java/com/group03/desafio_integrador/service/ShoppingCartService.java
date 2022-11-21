package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.entities.entities_enum.OrderStatusEnum;
import com.group03.desafio_integrador.entities.ShoppingCart;
import com.group03.desafio_integrador.repository.ShoppingCartRepository;
import com.group03.desafio_integrador.service.interfaces.IShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {

    private final ShoppingCartRepository repository;

    /**
     * Método responsável por salvar um novo shoppingCart.
     * @author Amanda Zotelli
     * @param shoppingCart - ShoppingCart
     * @return Retorna uma entidade do tipo shoppingCart.
     */
    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return repository.save(shoppingCart);
    }

    /**
     * Método responsável por atualizar um shoppingCart com o order status de finalizado.
     * @author Amanda Zotelli
     * @param id - Long
     * @return Retorna uma entidade do tipo shoppingCart.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public ShoppingCart update(Long id) {
       ShoppingCart shoppingCart = repository.findById(id).orElseThrow(() -> new NotFoundException("Shopping Cart not found!"));
       shoppingCart.setOrderStatus(OrderStatusEnum.FINALIZADO);
       return save(shoppingCart);
    }
}
