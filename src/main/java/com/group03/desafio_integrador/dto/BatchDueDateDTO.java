package com.group03.desafio_integrador.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
//@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchDueDateDTO {

    private Long batchNumber;
    private Long productId;
    private Long category;
    private LocalDate dueDate;
    private Integer quantity;
}
