package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.entities.DispatchPacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchPackingRepository extends JpaRepository<DispatchPacking, Long> {
}
