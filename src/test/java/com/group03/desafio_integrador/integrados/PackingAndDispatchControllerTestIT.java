package com.group03.desafio_integrador.integrados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group03.desafio_integrador.entities.CartProduct;
import com.group03.desafio_integrador.repository.CartProductRepository;
import com.group03.desafio_integrador.repository.DispatchPackingRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class PackingAndDispatchControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DispatchPackingRepository dispatchPackingRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllCartProduct() throws Exception {
        List<CartProduct> cartProductList = cartProductRepository.findAll();

        ResultActions response = mockMvc.perform(
                get("/api/v1/ordersByDispach/cart-product/all")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
                //.andExpect(content().json(String.valueOf(cartProductList)));
    }

    @Test
    void getAllFinishedPurchases() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/ordersByDispach/packing")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    void saveFinishedPurchases() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/ordersByDispach/save-finished-purchases")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated());
    }

    @Test
    void packProducts() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/ordersByDispach/pack-products")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated());
                //.andExpect(content().string("Produtos empacotados e prontos para o despacho!"));
    }

    @Test
    void getAllPackingForDispatch() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/ordersByDispach/dispatched-packages")
                        .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        ResultActions response = mockMvc.perform(patch("/api/v1/ordersByDispach/dispatch/{id}", '6')
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
                //.andExpect(jsonPath("$.batchId", CoreMatchers.is(batchId)));
    }

    @Test
    void deleteDispatchEntregues() throws Exception {
        ResultActions response = mockMvc.perform(delete("/api/v1/ordersByDispach/dispatch-entreges/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }
}