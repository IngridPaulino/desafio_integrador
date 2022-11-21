package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.BatchDueDateDTO;
import com.group03.desafio_integrador.dto.BatchDueDateStockDTO;
import com.group03.desafio_integrador.entities.Batch;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.entities.ProductAdvertising;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.repository.BatchRepository;
import com.group03.desafio_integrador.repository.InboundOrderRepository;
import com.group03.desafio_integrador.repository.ProductAdvertisingRepository;
import com.group03.desafio_integrador.service.interfaces.IBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService implements IBatchService {

    private final BatchRepository repository;

    private final InboundOrderRepository inboundOrderRepository;

    private final ProductAdvertisingRepository productRepository;

    /**
     * Método responsável por retornar o lote de acordo com o Id informado.
     * @author Gabriel Morais
     * @param id - Long
     * @return Retorna uma entidade do tipo Batch.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public Batch getById(Long id) throws NotFoundException {
        Optional<Batch> batch = repository.findById(id);
        return batch.orElseThrow(() -> new NotFoundException("Batch not found!"));
    }

    /**
     * Método responsável por salvar um novo lote.
     * @author Gabriel Morais
     * @param batch - Batch
     * @return Retorna uma entidade do tipo Batch.
     */
    @Override
    public Batch save(Batch batch) {
        return repository.save(batch);
    }

    /**
     * Método responsável por listar os lotes de produto de acordo com o Id do Produto
     * @author Amanda Zotelli
     * @param id - do tipo ProductAdvertising
     * @return Retorna uma lista de entidades do tipo Batch.
     */
    @Override
    public List<Batch> findBatchByProductId(ProductAdvertising id) {
        return repository.findAllByProductId(id);
    }

    /**
     * Método responsável por retornar os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param numberOfDays - Integer
     * @param section - String
     * @return Retorna uma entidade do tipo BatchDueDateStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public BatchDueDateStockDTO getAllDueDate(Integer numberOfDays, String section) {
        LocalDate currentDate = calculateDate(numberOfDays);
        List<BatchDueDateDTO> listBatchDTO = new ArrayList<>();

        List<Batch> batchList = repository.findAllByExpirationDateGreaterThan(currentDate);
        List<InboundOrder> inboundOrderList = inboundOrderRepository.findAll();

        List<InboundOrder> filterOrderSectionList = getFilterOrderSection(section, inboundOrderList);

        for (InboundOrder inboundOrder : filterOrderSectionList) {
            List<Batch> batchFilterList = inboundOrder.getBatchList();
            for (Batch batch : batchList) {
                List<Batch> compareBatchWithId = batchFilterList.stream().filter(item -> item.getBatchId().equals(batch.getBatchId())).collect(Collectors.toList());
                if(!compareBatchWithId.isEmpty()) {
                    BatchDueDateDTO batchDueDateDTO = buildBatchDueDateDTO(section, batch);
                    listBatchDTO.add(batchDueDateDTO);
                }
            }
        }

        if (listBatchDTO.isEmpty()) throw new NotFoundException("Not Found batch for expiration date and section");

        return BatchDueDateStockDTO.builder().batchDueDateStock(listBatchDTO).build();
    }

    /**
     * Método responsável por retornar uma lista de ordem de entrada filtrada por seção.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param section - String
     * @param inboundOrderList - List<InboundOrder>
     * @return Retorna uma lista do tipo List<InboundOrder>.
     */
    private static List<InboundOrder> getFilterOrderSection(String section, List<InboundOrder> inboundOrderList) {
        return inboundOrderList.stream()
                .filter(orderBatch -> section.equals(String.valueOf(orderBatch.getSectionId().getSectionId())))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por retornar uma data com os dias inseridos a mais.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param numberOfDays - Integer
     * @return Retorna uma data do tipo LocalDate.
     */
    private static LocalDate calculateDate(Integer numberOfDays) {
        return LocalDate.now().plusDays(numberOfDays);
    }

    /**
     * Método responsável por retornar os lotes dentro de uma data de validade e pertençam a uma categoria de produto
     * ordenados de forma crescente.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param numberOfDays - Integer
     * @param category - String
     * @param sorting - String
     * @return Retorna uma entidade do tipo BatchDueDateStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    @Override
    public BatchDueDateStockDTO getAllDueDateCategory(Integer numberOfDays, String category, String sorting) {
        LocalDate currentDate = calculateDate(numberOfDays);
        CategoryEnum categoryEnum = CategoryEnum.toEnum(category);
        List<BatchDueDateDTO> listBatchDTO = new ArrayList<>();

        List<Batch> batchList = repository.findAllByExpirationDateGreaterThan(currentDate);

        List<ProductAdvertising> productAdvertisingList = productRepository.findAllByCategory(categoryEnum);

        for (Batch batch: batchList) {
            for (ProductAdvertising product: productAdvertisingList) {
                if (batch.getProductId().getProductId().equals(product.getProductId())) {
                    BatchDueDateDTO batchDueDateDTO = buildBatchDueDateDTO(String.valueOf(categoryEnum.ordinal()), batch);
                    listBatchDTO.add(batchDueDateDTO);
                }
            }
        }

        if (listBatchDTO.isEmpty()) throw new NotFoundException("Not Found batch for expiration date and section");

        listBatchDTO = sortList(sorting, listBatchDTO);

        return BatchDueDateStockDTO.builder().batchDueDateStock(listBatchDTO).build();
    }

    /**
     *  Método responsável por retornar uma lista ordenada conforme parâmetro informado.
     * @author Gabriel Morais, Mariana Saraiva, Carolina Hakamada
     * @param listBatchDTO - lista contendo entidades do tipo BatchDueDateDTO
     * @param sorting - String
     * @return Retorna uma entidade do tipo BatchDueDateStockDTO.
     * @throws NotFoundException - NotFoundException
     */
    private static List<BatchDueDateDTO> sortList(String sorting, List<BatchDueDateDTO> listBatchDTO) {
        if ("DESC".equalsIgnoreCase(sorting)) {
            listBatchDTO = listBatchDTO.stream().sorted(Comparator.comparing(BatchDueDateDTO::getDueDate).reversed()).collect(Collectors.toList());
        } else {
            listBatchDTO = listBatchDTO.stream().sorted(Comparator.comparing(BatchDueDateDTO::getDueDate)).collect(Collectors.toList());
        }
        return listBatchDTO;
    }

    /**
     * Método responsável por executar o build de uma BatchDueDateDTO.
     *
     * @param section - String
     * @param batch - Batch
     * @return Retorna uma BatchDueDateDTO.
     * @author Carolina Hakamada, Gabriel Morais, Mariana Saraiva
     */
    private static BatchDueDateDTO buildBatchDueDateDTO(String section, Batch batch) {
        return BatchDueDateDTO.builder()
                .batchNumber(batch.getBatchId())
                .productId(batch.getProductId().getProductId())
                .category(Long.valueOf(section))
                .dueDate(batch.getExpirationDate())
                .quantity(batch.getProductQuantity())
                .build();
    }
}
