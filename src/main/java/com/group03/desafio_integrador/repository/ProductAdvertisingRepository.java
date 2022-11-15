package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAdvertisingRepository extends JpaRepository<ProductAdvertising, Long> {
    List<ProductAdvertising> findAllByCategory(CategoryEnum categoryEnum);

    interface IDispatchPackingService {
    }
}
