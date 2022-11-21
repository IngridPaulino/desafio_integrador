package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.Section;

public interface ISectionService {

    /**
     * Método responsável por retornar a sessão de acordo com o Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @return Retorna uma entidade do tipo Section.
     */
    Section getById(Long id);

    /**
     * Método responsável por retornar a categoria da sessão.
     * @author Gabriel Morais
     * @param category - CategoryEnum
     * @return Retorna uma entidade do tipo Section.
     */
    Section findByCategory(CategoryEnum category);

    /**
     * Método responsável por salvar nova sessão.
     * @author Gabriel Morais
     * @param section - Section
     * @return Retorna uma entidade do tipo Section.
     */
    Section save(Section section);
}