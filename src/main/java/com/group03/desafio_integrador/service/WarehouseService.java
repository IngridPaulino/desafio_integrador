package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductWarehouseDTO;
import com.group03.desafio_integrador.dto.WarehouseStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.entities.Warehouse;
import com.group03.desafio_integrador.repository.InboundOrderRepository;
import com.group03.desafio_integrador.repository.WarehouseRepository;
import com.group03.desafio_integrador.service.interfaces.IProductAdvertisingService;
import com.group03.desafio_integrador.service.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository repository;

    private final InboundOrderRepository inboundOrderRepository;

    private final IProductAdvertisingService productService;

    /**
     * Método responsável por retornar o armazém conforme Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @return Retorna uma entidade do tipo Warehouse.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public Warehouse getById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Warehouse not found!"));
    }

    /**
     * Método responsável por retornar a lista de todos os armazéns cadastrados.
     * @author grupo3
     * @return Retorna uma lista contendo entidades do tipo Warehouse.
     */
    @Override
    public List<Warehouse> getAll() {
        return repository.findAll();
    }

    /**
     * Método responsável por retornar o estoque de um produto em todos os armazéns.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param id - Long
     * @return Retorna uma entidade do tipo productWarehouseDTO.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public ProductWarehouseDTO getAllStockProductWarehouse(Long id) {
        productService.getById(id);

        List<InboundOrder> inboundOrderList = inboundOrderRepository.findAll();
        ProductWarehouseDTO productWarehouseDTO = buildProductWarehouseDTO(id);

        for(InboundOrder inboundOrder: inboundOrderList) {
            Integer quantitySum = calculateQuantityStockInBatch(id, inboundOrder);
            WarehouseStockDTO warehouseStockDTO = buildWarehouseStockDTO(inboundOrder, quantitySum);

            if (quantitySum > 0) {
                List<WarehouseStockDTO> warehouseStockDTOList = getFilterWarehouseById(productWarehouseDTO, warehouseStockDTO);

                if (warehouseStockDTOList.isEmpty()) {
                    productWarehouseDTO.getWarehouses().add(warehouseStockDTO);
                } else {
                    incrementQuantityStockInWarehouse(productWarehouseDTO, quantitySum, warehouseStockDTO);
                }
            }
        }

        if (productWarehouseDTO.getWarehouses().isEmpty()) throw new NotFoundException("Product not registered in any warehouse");

        return productWarehouseDTO;
    }

    /**
     * Método responsável por construir uma response personalizada - builder.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param inboundOrder - InboundOrder
     * @param quantitySum - Integer
     * @return Retorna builder do tipo WarehouseDTO.
     */
    private static WarehouseStockDTO buildWarehouseStockDTO(InboundOrder inboundOrder, Integer quantitySum) {
        return WarehouseStockDTO.builder()
                .warehouseCode(inboundOrder.getWarehouseId().getWarehouseId())
                .totalQuantity(quantitySum.toString())
                .build();
    }

    /**
     * Método responsável por construir uma response personalizada - builder.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param id - Long
     * @return Retorna builder do tipo productWarehouseDTO.
     */
    private static ProductWarehouseDTO buildProductWarehouseDTO(Long id) {
        return ProductWarehouseDTO.builder()
                .productId(id)
                .warehouses(new ArrayList<>())
                .build();
    }

    /**
     * Método responsável por incrementar a quantidade de estoque de um produto no armazém.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param productWarehouseDTO - ProductWarehouseDTO
     * @param quantitySum - Integer
     * @param warehouseStockDTO - WarehouseStockDTO
     */
    private static void incrementQuantityStockInWarehouse(ProductWarehouseDTO productWarehouseDTO,
                                                          Integer quantitySum,
                                                          WarehouseStockDTO warehouseStockDTO) {
        productWarehouseDTO.getWarehouses().forEach(warehouse -> {
            if(warehouse.getWarehouseCode().equals(warehouseStockDTO.getWarehouseCode())) {
                Integer sum = Integer.parseInt(warehouse.getTotalQuantity()) + quantitySum;
                warehouse.setTotalQuantity(String.valueOf(sum));
            }
        });
    }

    /**
     * Método responsável por filtrar os armazéns pelo Id dentro de uma lista de estoque de produtos.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param productWarehouseDTO - ProductWarehouseDTO
     * @param warehouseStockDTO - WarehouseStockDTO
     * @return Retorna uma lista filtrada com os dados do armazém que possuem estoque de um produto específico.
     */
    private static List<WarehouseStockDTO> getFilterWarehouseById(ProductWarehouseDTO productWarehouseDTO, WarehouseStockDTO warehouseStockDTO) {
        return productWarehouseDTO.getWarehouses().stream()
                .filter(warehouse -> warehouseStockDTO.getWarehouseCode().equals(warehouse.getWarehouseCode()))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por calcular o estoque de um produto existente em todos os lotes contidos no armazém.
     * @author Gabriel Morais, Rosalia Padoin, Mariana Saraiva
     * @param id- Long
     * @param inboundOrder - InboundOrder
     * @return Retorna um valor total do estoque do tipo Integer.
     */
    private static Integer calculateQuantityStockInBatch(Long id, InboundOrder inboundOrder) {
        return inboundOrder.getBatchList().stream()
                .filter(batch -> batch.getProductId().getProductId().equals(id))
                .map(Batch::getProductQuantity)
                .reduce(0, Integer::sum);
    }
}
