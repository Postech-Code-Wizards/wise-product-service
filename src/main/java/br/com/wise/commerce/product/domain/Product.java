package br.com.wise.commerce.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private String sku;
    private Category category;
    private Double price;
    private Boolean inStock;
    private Integer stock;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public void createSku(String sku) {
        this.sku = sku;
    }
}
