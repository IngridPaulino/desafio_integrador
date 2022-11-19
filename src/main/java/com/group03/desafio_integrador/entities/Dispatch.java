package com.group03.desafio_integrador.entities;

import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.entities_enum.DispatchStatusEnum;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Packing;

    @NotNull
    private Long buyer_id;

    @NotNull
    private CategoryEnum category;

    @NotNull
    private DispatchStatusEnum status;

}
