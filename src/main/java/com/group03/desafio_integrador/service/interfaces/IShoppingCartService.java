package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.entities.ShoppingCart;

public interface IShoppingCartService {

    /**
     * Método responsável por salvar um novo shoppingCart.
     * @author Amanda Zotelli
     * @param shoppingCart - ShoppingCart
     * @return Retorna uma entidade do tipo shoppingCart.
     */
    ShoppingCart save(ShoppingCart shoppingCart);

    /**
     * Método responsável por atualizar um shoppingCart com o order status de finalizado.
     * @author Amanda Zotelli
     * @param id - Long
     * @return Retorna uma entidade do tipo shoppingCart.
     */
    ShoppingCart update(Long id);
}