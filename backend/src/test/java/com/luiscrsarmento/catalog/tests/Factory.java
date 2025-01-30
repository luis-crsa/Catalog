package com.luiscrsarmento.catalog.tests;

import com.luiscrsarmento.catalog.dto.ProductDTO;
import com.luiscrsarmento.catalog.entities.Category;
import com.luiscrsarmento.catalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good phone", 800.0, "https://img.com.img.png", Instant.parse("2024-12-31T03:00:00z"));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDto() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Electronics");
    }

}
