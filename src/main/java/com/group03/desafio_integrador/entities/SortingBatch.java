package com.group03.desafio_integrador.entities;

import com.group03.desafio_integrador.dto.BatchDTO;
import com.group03.desafio_integrador.entities.entities_enum.SortingEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class SortingBatch {
    private SortingEnum filter;
    private List<Batch> batchList;

    /**
     * Método responsável por ordenar uma lista de lotes por lote.
     * @author Amanda Zotelli, Rosalia Padoin
     * @return Retorna uma lista de lotes ordenados por lote.
     */
    public static List<BatchDTO> ordenatedByBatch(List<BatchDTO> batches) {
       return batches.stream().sorted(Comparator.comparing(BatchDTO::getBatchId))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por ordenar uma lista de lotes pela quantidade de produtos no lote.
     * @author Amanda Zotelli, Rosalia Padoin
     * @return Retorna uma lista de lotes ordenados pela quantidade de produtos no lote.
     */
    public static List<BatchDTO> ordenatedByQuantity(List<BatchDTO> batches) {
        return batches.stream().sorted(Comparator.comparing(BatchDTO::getQuantity))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por ordenar uma lista de lotes pela data de vencimento dos produtos no lote.
     * @author Amanda Zotelli, Rosalia Padoin
     * @return Retorna uma lista de lotes ordenados pela data de vencimento dos produtos no lote.
     */
    public static List<BatchDTO> ordenatedByExpirationDate(List<BatchDTO> batches) {
        return batches.stream().sorted(Comparator.comparing(BatchDTO::getExpirationDate))
                .collect(Collectors.toList());
    }
}
