package com.group03.desafio_integrador.entities;

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
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dispatch;

    @NotNull
    @OneToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @NotNull
    private CategoryEnum category;

    @NotNull
    private DispatchStatusEnum status;

}
