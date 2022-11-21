package com.group03.desafio_integrador.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.entities_enum.DispatchStatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DispatchPacking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Packing;

    @NotNull
    private Long product_id;

    @NotNull
    private Long buyer_id;

    @NotNull
    private CategoryEnum category;
}

