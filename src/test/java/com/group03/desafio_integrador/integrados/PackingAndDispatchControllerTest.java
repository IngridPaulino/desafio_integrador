package com.group03.desafio_integrador.integrados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group03.desafio_integrador.controller.PackingAndDispatchController;
import com.group03.desafio_integrador.entities.InboundOrder;
import com.group03.desafio_integrador.repository.InboundOrderRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class PackingAndDispatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PackingAndDispatchController packingAndDispatchController;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllFinishedPurchases() throws Exception {
       // a
        //ResultActions response = mockMvc.perform(
               // get("/api/v1/orders/packing")
                       // .contentType(MediaType.APPLICATION_JSON) );

        //response.andExpect(status().isOk());
        //assertThat(inboudOrders).asList();
    }

    @Test
    void saveFinishedPurchases() {
    }

    @Test
    void packProducts() {
    }

    @Test
    void getAllPackingForDispatch() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteDispatchEntregues() {
    }
}