package com.group03.desafio_integrador.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerId;

    @NotBlank
    @Size(min = 3, max = 40, message= "The buyer name must have at least 3 characters and cannot exceed 40 characters")
    private String buyerName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    //@OneToOne(mappedBy = "buyer", cascade = CascadeType.PERSIST)
    //@JsonIgnoreProperties("buyer")
    //@JsonIgnore
    //private Dispatch dispatch;
}
