package com.group03.desafio_integrador.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAdvertising {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long productId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT")
    @NotNull
    private LocalDate fabricationDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    @NotNull
    private LocalDateTime fabricationTime;

    @NotBlank
    @Size(min = 4, max = 40, message = "The product name must have at least 4 characters and cannot exceed 40 characters")
    private String productName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties("productAdvertisingList")
    @Valid
    private Seller seller;

    @Valid
    private CategoryEnum category;

    private BigDecimal productPrice;

    @Transient
    private Integer quantity;
}
