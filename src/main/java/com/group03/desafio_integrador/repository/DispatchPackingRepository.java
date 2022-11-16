package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.dto.DispatchDTO;
import com.group03.desafio_integrador.entities.DispatchPacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchPackingRepository extends JpaRepository<DispatchPacking, Long> {
    @Query(value = "SELECT buyer_id, category FROM dispatch_packing GROUP By buyer_id, category", nativeQuery = true)
    List<Object[]> packingByDispatch();
}
