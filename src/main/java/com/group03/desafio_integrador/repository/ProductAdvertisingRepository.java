package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAdvertisingRepository extends JpaRepository<ProductAdvertising, Long> {

    /**
     * Método responsável por retornar todos os produtos cadastrados por categoria.
     * @param categoryEnum - CategoryEnum
     * @return Retorna uma lista de entidades do tipo ProductAdvertising que possuem a categoria informada.
     * @throws NotFoundException - NotFoundException
     * @author Mariana Saraiva
     */
    List<ProductAdvertising> findAllByCategory(CategoryEnum categoryEnum);

    interface IDispatchPackingService {
    }
}
