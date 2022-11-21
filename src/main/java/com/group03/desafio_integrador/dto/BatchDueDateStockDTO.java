package com.group03.desafio_integrador.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Setter
@Getter
@Builder
public class BatchDueDateStockDTO {

    List<BatchDueDateDTO> batchDueDateStock;
}
