package com.group03.desafio_integrador.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group03.desafio_integrador.entities.ShoppingCart;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import lombok.*;

@Builder
public class PackingOrderDTO {
    private Long cart_product_id;
    private Long product_id;
    private Long buyer_id;
    private CategoryEnum category;
    private String order_status;
    @JsonIgnore
    private ShoppingCart shopping_cart;

    public PackingOrderDTO(Long cart_product_id,
                           Long product_id,
                           Long buyer_id,
                           CategoryEnum category,
                           String order_status,
                           ShoppingCart shopping_cart) {
        this.cart_product_id = cart_product_id;
        this.product_id = product_id;
        this.buyer_id = buyer_id;
        this.category = category;
        this.order_status = order_status;
        this.shopping_cart = shopping_cart;
    }

    public Long getCart_product_id() {
        return cart_product_id;
    }
    public Long getProduct_id() {
        return product_id;
    }
    public Long getBuyer_id() {
        return buyer_id;
    }
    public CategoryEnum getCategory() {
        return category;
    }
    public String getOrder_status() {
        return order_status;
    }
    public ShoppingCart getShopping_cart() {
        return shopping_cart;
    }
}