package com.group03.desafio_integrador.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartProductId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductAdvertising productAdvertising;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @JsonIgnoreProperties("buyer")
    private ShoppingCart shoppingCart;

    private Integer quantity;
}
