package com.group03.desafio_integrador.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
//@Setter
@Builder
public class ProductWarehouseDTO {
    private Long productId;
    private List<WarehouseStockDTO> warehouses;
}
