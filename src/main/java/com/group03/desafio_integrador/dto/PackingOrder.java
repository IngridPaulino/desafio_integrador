package com.group03.desafio_integrador.dto;

import com.group03.desafio_integrador.entities.ShoppingCart;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;

public interface PackingOrder {
    Long getCart_product_id();
    Long getProduct_id();
    ShoppingCart getShopping_cart();
    Long getBuyer_id();
    CategoryEnum getCategory();
    String getOrder_status();
}
