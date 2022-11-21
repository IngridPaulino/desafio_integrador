package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.Section;
import com.group03.desafio_integrador.repository.SectionRepository;
import com.group03.desafio_integrador.service.interfaces.ISectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService implements ISectionService {

    private final SectionRepository repository;

    /**
     * Método responsável por retornar a sessão conforme Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @throws NotFoundException - NotFoundException
     * @return Retorna uma entidade do tipo Section.
     */
    @Override
    public Section getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Section not found!"));
    }

    /**
     * Método responsável por retornar a categoria da sessão.
     * @author Gabriel Morais
     * @param category - CategoryEnum
     * @throws NotFoundException - NotFoundException
     * @return Retorna uma entidade do tipo Section conforme categoria informada.
     */
    @Override
    public Section findByCategory(CategoryEnum category) {
        Section sectionCategory = repository.findByCategory(category);

        if (sectionCategory == null) {
            throw new NotFoundException("Category not found!");
        }
        return sectionCategory;
    }

    /**
     * Método responsável por salvar nova sessão.
     * @author Gabriel Morais
     * @param section - Section
     * @return Retorna uma entidade do tipo Section.
     */
    @Override
    public Section save(Section section) {
        return repository.save(section);
    }
}
