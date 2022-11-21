package com.group03.desafio_integrador.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
//@Setter
@Builder
public class BatchDTO {
    private Long batchId;
    private Integer quantity;
    private LocalDate expirationDate;
}
