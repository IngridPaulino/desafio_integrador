package com.group03.desafio_integrador.service;

import com.group03.desafio_integrador.advisor.ValidationErrorDetail;
import com.group03.desafio_integrador.advisor.exceptions.NotFoundException;
import com.group03.desafio_integrador.dto.ProductDTO;
import com.group03.desafio_integrador.dto.PurchaseOrderDTO;
import com.group03.desafio_integrador.dto.ShoppingCartTotalDTO;
import com.group03.desafio_integrador.entities.*;
import com.group03.desafio_integrador.entities.entities_enum.CategoryEnum;
import com.group03.desafio_integrador.entities.entities_enum.OrderStatusEnum;
import com.group03.desafio_integrador.repository.ProductAdvertisingRepository;
import com.group03.desafio_integrador.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductAdvertisingService implements IProductAdvertisingService {
    private final ProductAdvertisingRepository repository;
    private final IShoppingCartService shoppingCartService;
    private final IBuyerService buyerService;
    private final IBatchService batchService;
    private final ICartProductService cartService;

    /**
     * Método responsável por retornar o produto de acordo com o Id informado.
     *
     * @param id - Long
     * @return ProductAdvertising - Retorna uma entidade do tipo ProductAdvertising.
     * @throws NotFoundException - NotFoundException
     * @author Gabriel Morais
     */
    @Override
    public ProductAdvertising getById(Long id) throws NotFoundException {
        Optional<ProductAdvertising> product = repository.findById(id);
        return product.orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    /**
     * Método responsável por retornar todos os produtos cadastrados.
     *
     * @return Retorna uma lista entidade do tipo ProductAdvertising.
     * @throws NotFoundException - NotFoundException
     * @author Mariana Saraiva
     */
    @Override
    public List<ProductAdvertising> getAll() throws NotFoundException {
        List<ProductAdvertising> productAdvertisingList = repository.findAll();
        if (productAdvertisingList.isEmpty()) {
            throw new NotFoundException("Product Advertising not found");
        }

        return productAdvertisingList;
    }

    /**
     * Método responsável por retornar todos os produtos cadastrados por categoria.
     * @param category - String
     * @return Retorna uma lista de entidades do tipo ProductAdvertising que possuem a categoria informada.
     * @throws NotFoundException - NotFoundException
     * @author Mariana Saraiva
     */
    @Override
    public List<ProductAdvertising> getAllByCategory(String category) {
        CategoryEnum enumCategory = CategoryEnum.toEnum(category);
        List<ProductAdvertising> productAdvertisingList = repository.findAllByCategory(enumCategory);

        if (productAdvertisingList.isEmpty()) {
            throw new NotFoundException("Category not found");
        }

        return productAdvertisingList;
    }

    /**
     * Método responsável por registrar os pedidos
     * @param purchase - PurchaseOrderDTO
     * @return Retorna um dto do tipo ShoppingCartTotalDTO.
     * @author Amanda Zotelli, Gabriel Morais
     */
    @Override
    public ShoppingCartTotalDTO registerOrder(PurchaseOrderDTO purchase) {
        verifyStock(purchase);
        Buyer buyer = buyerService.getById(purchase.getBuyerId());
        Set<ProductAdvertising> products = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (ProductDTO product : purchase.getProducts()) {
            ProductAdvertising productAdvertising = getById(product.getProductId());
            productAdvertising.setQuantity(product.getQuantity());
            products.add(getById(product.getProductId()));
            BigDecimal productPrice = productAdvertising.getProductPrice().multiply(new BigDecimal(product.getQuantity()));
            totalPrice = totalPrice.add(productPrice);
        }

        ShoppingCart shoppingCart = shoppingCartBuilder(buyer, totalPrice);
        saveShoppingCart(products, shoppingCart);

        return ShoppingCartTotalDTO.builder()
                .totalPrice(Double.valueOf(String.valueOf(totalPrice)))
                .build();
    }

    /**
     * Método responsável por estruturar o Builder para retorno do ShoppingCart.
     * @param buyer - Buyer
     * @param totalPrice - BigDecimal
     * @return Retorna uma entidade do tipo ShoppingCart.
     * @author Amanda Zotelli
     */
    private static ShoppingCart shoppingCartBuilder(Buyer buyer, BigDecimal totalPrice) {
        return ShoppingCart.builder()
                .buyer(buyer)
                .date(LocalDate.now())
                .orderStatus(OrderStatusEnum.ABERTO)
                .totalCartPrice(Double.valueOf(String.valueOf(totalPrice)))
                .build();
    }

    /**
     * Método responsável por salvar(associar) o produto ao ShoppingCart.
     * @param products - Set do tipo ProductAdvertising
     * @param shoppingCart - ShoppingCart
     * @author Amanda Zotelli
     */
    protected void saveShoppingCart(Set<ProductAdvertising> products, ShoppingCart shoppingCart) {
        ShoppingCart cartSaved = shoppingCartService.save(shoppingCart);
        List<CartProduct> cartProductList = new ArrayList<>();

        for (ProductAdvertising product : products) {
            CartProduct cartProduct = CartProduct.builder()
                    .productAdvertising(product)
                    .shoppingCart(cartSaved)
                    .quantity(product.getQuantity())
                    .build();

            cartProductList.add(cartProduct);
        }
        cartService.saveAll(cartProductList);
    }

    /**
     * Método responsável por verificar se existe estoque para realizar a venda e atualizar o estoque no lote.
     * @param purchase - PurchaseOrderDTO
     * @author Gabriel Morais, Mariana Saraiva
     */

    protected void verifyStock(PurchaseOrderDTO purchase) {
        List<ValidationErrorDetail> errorDetails = new ArrayList<>();

        for (ProductDTO product : purchase.getProducts()) {
            ProductAdvertising productId = ProductAdvertising.builder().productId(product.getProductId()).build();

            List<Batch> batchList = batchService.findBatchByProductId(productId);

            if (batchList.isEmpty()) throw new NotFoundException("No batch found with this product!");

            for (Batch batchOrder : batchList) {
                List<ValidationErrorDetail> errorDetailsBatch = new ArrayList<>();

                verifyProductExists(errorDetailsBatch, productId.getProductId());
                verifyProductExpirationDate(errorDetailsBatch, batchOrder, productId.getProductId());
                verifyProductStockQuantity(errorDetailsBatch, product, batchOrder, productId.getProductId());

                if (errorDetailsBatch.isEmpty()) {
                    return;
                } else if (batchList.indexOf(batchOrder) == batchList.size() - 1 && !errorDetailsBatch.isEmpty()) {
                    errorDetails.addAll(errorDetailsBatch);
                }
            }
        }

        if (!errorDetails.isEmpty()) throw new NotFoundException("Products not found", errorDetails);
    }

    /**
     * Método responsável por verificar se a data de validade é superior há 3 semanas ao ser disponibilizado a venda.
     * @param errorDetails - lista do tipo ValidationErrorDetail
     * @param batch - Batch
     * @param idProduct - Long
     * @author Amanda Zotelli
     */
    public static void verifyProductExpirationDate(List<ValidationErrorDetail> errorDetails, Batch batch, Long idProduct) {
        if (batch.getExpirationDate().isBefore(LocalDate.now().plusWeeks(3))) {
            errorDetails.add(
                    ValidationErrorDetail.builder()
                            .field("expirationDate").message("Product " + idProduct + " with validation date expired!")
                            .build());
        }
    }

    /**
     * Método responsável por verificar a quantidade disponível de produtos no estoque.
     * @param errorDetails - lista do tipo ValidationErrorDetail
     * @param product - ProductDTO
     * @param batch - Batch
     * @param idProduct - Long
     * @author Amanda Zotelli
     */
    protected void verifyProductStockQuantity(List<ValidationErrorDetail> errorDetails, ProductDTO product, Batch batch, Long idProduct) {
        if (batch.getProductQuantity() >= product.getQuantity()) {
            if (errorDetails.isEmpty()) {
                Integer updatedQuantity = batch.getProductQuantity() - product.getQuantity();
                batch.setProductQuantity(updatedQuantity);
            }
        } else {
            errorDetails.add(
                    ValidationErrorDetail.builder()
                            .field("productQuantity").message("Product " + idProduct + " quantity not available in stock!")
                            .build());
        }
    }

    /**
     * Método responsável por verificar se o produto existe(é cadastrado no sistema).
     * @param errorDetails - lista do tipo ValidationErrorDetail
     * @param idProduct - Long
     * @author Amanda Zotelli
     */
    protected void verifyProductExists(List<ValidationErrorDetail> errorDetails, Long idProduct) {
        try {
            getById(idProduct);
        } catch (Exception ex) {
            errorDetails.add(
                    ValidationErrorDetail.builder()
                            .field("productId").message("Product " + idProduct + " not found!")
                            .build());
        }
    }
}
