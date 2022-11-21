package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.entities.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
}
