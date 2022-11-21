package com.group03.desafio_integrador.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    private String name;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("manager")
    private Warehouse warehouse;

}
