package com.group03.desafio_integrador.dto;

import com.group03.desafio_integrador.entities.Buyer;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class PackingOrderDTO {
    private Long cart_product_id;
    private Long product_id;
    private Long seller;
    private Long buyer_id;
    private CategoryEnum category;
    private String order_status;
}