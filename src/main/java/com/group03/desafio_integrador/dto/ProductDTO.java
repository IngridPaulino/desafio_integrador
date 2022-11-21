package com.group03.desafio_integrador.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
@Builder
public class ProductDTO {
    private Long productId;
    private Integer quantity;
}
