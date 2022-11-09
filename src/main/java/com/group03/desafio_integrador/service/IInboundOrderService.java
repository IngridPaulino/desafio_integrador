package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.entities.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    InboundOrder save(InboundOrder inboundOrder);

    List<InboundOrder> getAll();
}
