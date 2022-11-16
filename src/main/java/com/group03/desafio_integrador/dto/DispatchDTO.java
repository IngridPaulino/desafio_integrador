package com.group03.desafio_integrador.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DispatchDTO {
    private BigInteger buyer_id;
    private Integer category;
}
