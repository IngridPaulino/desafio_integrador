package com.group03.desafio_integrador.service.interfaces;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.PackingOrderDTO;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.entities.Dispatch;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface IPackingAndDispatchService {
    /**
     * Método responsável por retornar todos os carrinhos de compra.
     * @return Retorna uma lista do tipo CartProduct
     * @author Ingrid Paulino
     */
    List<CartProduct> findAllCartProduct();

    /**
     * Método responsável por retornar os carrinhos de compra com status de finalizado - coleta
     * @return Retorna uma lista do tipo PackingOrderDTO
     * @author Ingrid Paulino
     */
    List<PackingOrderDTO> getAllFinishedPurchases();

    /**
     * Método responsável por deletar os carrinhos de compra que foram salvos/registrados na tabela de embalagem
     * @author Ingrid Paulino
     */
    void deleteAllCartProductFinished();

    /**
     * Método responsável por salvar todas as compras finalizadas na tabela de embalagem
     * @author Ingrid Paulino
     */
    void saveFinishedPurchases();

    /**
     * Método responsável por deletar os carrinhos registrados na tabela de embalagem.
     * @author Ingrid Paulino
     */
    void deleteAllCartProductEmbalados();

    /**
     * Método responsável por empacotar na mesma embalagem os produtos com categoria e comprador iguais e salvar esses dados na tabela de despacho
     * @author Ingrid Paulino
     */
    void packagedProductsFromSameBuyerAndCategory();

    /**
     * Método responsável por retornar todos os pacotes que estão prontos para entrega
     * @return retorna uma lista de Dispatch
     * @author Ingrid Paulino
     */
    List<Dispatch> getAllPackingForDispatch();

    /**
     * Método responsável por atualizar status da entrega para entregue e encaminhar um email avisando para o comprador que o produto foi entrgue.
     * @author Ingrid Paulino
     * @param id -Long
     * @return Retorna uma entidade do tipo Dispatch ou uma string caso o pacote não sejá encontrado
     * @throws NotFoundException - NotFoundException
     */
    Dispatch updateStatusDispatch(Long id) throws NotFoundException;

    /**
     * Rota responsável por deletar as entregas com status de entrgue.
     * @author Ingrid Paulino
     * @return Retorna uma string.
     */
    AtomicReference<Integer> deleteAllPackingsEntregue();
}
