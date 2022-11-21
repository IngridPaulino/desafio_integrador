package com.group03.desafio_integrador.entities.entities_enum;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;

public enum SortingEnum {
    L, // Lote - Batch
    Q, // Quantity
    V; // Expiration Date

    /**
     * Método responsável por converter uma String em Enum.
     * @author Amanda Zotelli, Rosalia Padoin
     * @return SortingEnum - Retorna um enum do tipo SortingEnum.
     * @throws NotFoundException - NotFoundException
     */
    public static SortingEnum toEnum(String string) {
        SortingEnum enumSorting;

        try {
            enumSorting = SortingEnum.valueOf(string);
        } catch (Exception ex){
            throw new NotFoundException("This sorting is not valid");
        }
        return enumSorting;
    }
}
