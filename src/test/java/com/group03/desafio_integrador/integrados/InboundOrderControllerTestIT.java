package com.group03.desafio_integrador.integrados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group03.desafio_integrador.dto.BatchStockDTO;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.repository.InboundOrderRepository;
import com.group03.desafio_integrador.utils.mocks.TestsMocks;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class InboundOrderControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    List<Batch> batchList = new ArrayList<>();
    BatchStockDTO batchDTO;
    private InboundOrder mockCreateInboundOrder;

    private Batch mockBatch;

    @BeforeEach
    void setUp() {

        Batch mockBatchList = TestsMocks.createBatch();

        mockBatch = TestsMocks.mockBatch();

        batchList.add(mockBatchList);

        batchDTO = new BatchStockDTO(batchList);

        mockCreateInboundOrder = TestsMocks.mockCreateInboundOrder();

        InboundOrder mockInboundOrder = TestsMocks.mockInboundOrder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll_returnListInboundOrder_whenNotEmpty() throws Exception {
        List<InboundOrder> inboudOrders = inboundOrderRepository.findAll();

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isOk());
        assertThat(inboudOrders).asList();
    }

    @Test
    void update_returnBatch_whenValidData() throws Exception {
        Integer batchId = Math.toIntExact(mockBatch.getBatchId());
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockBatch)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchId", CoreMatchers.is(batchId)));
    }

    @Test
    void save_throwsNotAcceptableException_whenProductDoesNotBelongToTheSection() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestsMocks.mockCreateErrorInboundOrder())));

        response.andExpect(status().isNotAcceptable());
    }
}