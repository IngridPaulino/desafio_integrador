package com.group03.desafio_integrador.dto;


import com.group03.desafio_integrador.entities.Batch;
import lombok.*;

import java.util.List;

/**
 * Entidade responsável por representar um lote no armazém.
 * @author Gabriel de Morais
 */

@Getter
//@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockDTO {
    private List<Batch> batchStock;
}

