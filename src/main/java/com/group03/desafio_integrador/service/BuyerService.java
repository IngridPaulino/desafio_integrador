package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.entities.Buyer;
import com.group03.desafio_integrador.repository.BuyerRepository;
import com.group03.desafio_integrador.service.interfaces.IBuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerService implements IBuyerService {

    private final BuyerRepository repository;

    /**
     * Método responsável por retornar o comprador conforme Id informado.
     * @author Amanda Zotelli
     * @param id - Long
     * @return Retorna uma entidade do tipo Buyer.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public Buyer getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Buyer not found!"));
    }

}
