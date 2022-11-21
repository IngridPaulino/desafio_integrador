package com.group03.desafio_integrador.entities.entities_enum;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;

public enum CategoryEnum {
    FS, // 0 - Fresco
    RF, // 1 - Refrigerado
    FF; // 2 - Congelado

    /**
     * Método responsável por converter uma String em Enum.
     * @author Mariana Saraiva
     * @return Retorna um enum do tipo CategoryEnum.
     * @throws NotFoundException - NotFoundException
     */
    public static CategoryEnum toEnum(String string) {
        CategoryEnum enumCategory;

        try {
            enumCategory = CategoryEnum.valueOf(string);
        } catch (Exception ex){
            throw new NotFoundException("This category is not valid");
        }

        return enumCategory;
    }

}
