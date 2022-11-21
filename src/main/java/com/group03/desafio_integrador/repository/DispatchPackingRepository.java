package com.group03.desafio_integrador.repository;

import com.group03.desafio_integrador.dto.PackingOrder;
import com.group03.desafio_integrador.entities.DispatchPacking;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchPackingRepository extends JpaRepository<DispatchPacking, Long> {
    /**
     * Método responsável por agrupar as compras do cliente com a categoria dos produtos em comum;
     * @author Ingrid PaulinoProductAdvertising
     * @return Retorna uma Lista de packing Order.
     */
    //@Query(value = "SELECT d.buyer_id, d.category FROM dispatch_packing d GROUP By d.buyer_id, d.category", nativeQuery = true)
    //List<PackingOrder> packingByDispatch();
    @Query(value = "SELECT d.buyer_id, d.category FROM dispatch_packing d GROUP By d.buyer_id, d.category", nativeQuery = true)
    List<PackingOrder> packingByDispatch();

    //@Query(value = "SELECT d FROM DispatchPacking d GROUP By d.buyer, d.category")
    //List<DispatchPacking> packingByDispatch();

    /**
     * Método responsável por listar os lotes de produto conforme o  buyer_id e category do Produto
     * @author Ingrid PaulinoProductAdvertising
     * @param buyerId - Long
     * @param category - CategoryEnum
     * @return Retorna uma Lista de DispatchPacking
     */
    @Query(value = "SELECT * FROM fresh.dispatch_packing WHERE buyer_id=? AND category=?;", nativeQuery = true)
    List<DispatchPacking> findByCategoryAndBuyer(Long buyerId, CategoryEnum category);

}
